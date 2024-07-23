FROM openjdk:17-jdk-slim

LABEL authors="robinyeon"

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]