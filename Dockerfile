#FROM ubuntu:latest
#LABEL authors="lenovo"

#ENTRYPOINT ["top", "-b"]

# Use Java 21 image
FROM eclipse-temurin:21-jdk

# App jar name
ARG JAR_FILE=target/*.jar

# Copy jar into container
COPY ${JAR_FILE} app.jar

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","/app.jar"]