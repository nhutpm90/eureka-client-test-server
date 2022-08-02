FROM openjdk:11-slim
WORKDIR /opt/spring-cloud-playground
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]