# Use Java base image
FROM openjdk:21-jdk

# Accept JAR file path as a build argument
ARG JAR_FILE=app/*.jar

# Copy JAR file into the container
COPY ${JAR_FILE} app.jar

# Define the default command to run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]