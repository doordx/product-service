FROM openjdk:17-jdk-slim
MAINTAINER Manishkumar Khatri

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]