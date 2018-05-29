FROM openjdk:8
ARG JAR_PATH
ADD $JAR_PATH /todo-cljs-client/app.jar
EXPOSE 3005
CMD ["java", "-jar", "/todo-cljs-client/app.jar"]