FROM openjdk:8

ARG JAR_PATH
ARG RESOURCE_PUBLIC_PATH

ADD $JAR_PATH /todo-cljs-client/app.jar
ADD $RESOURCE_PUBLIC_PATH /todo-cljs-client/resources/public

EXPOSE 3005

CMD ["java", "-jar", "/todo-cljs-client/app.jar"]