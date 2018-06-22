FROM ubuntudesign/sass as sass_builder
WORKDIR /todo_cljs_client
ADD resources ./resources
RUN sass resources/sass/index.scss index.css

FROM clojure:lein-2.8.1 as clojure_builder
WORKDIR /todo_cljs_client
ADD project.clj .
ADD src ./src
ADD resources ./resources
COPY --from=sass_builder \
  /todo_cljs_client/index.css \
  ./resources/public/css/index.css
RUN lein build-client:prod
RUN lein build-server:prod

FROM openjdk:8
WORKDIR /todo_cljs_client
COPY --from=clojure_builder \
  /todo_cljs_client/target/uberjar/todo-cljs-client-standalone.jar \
  ./app.jar
EXPOSE 3005
CMD ["java", "-jar", "/todo_cljs_client/app.jar"]
