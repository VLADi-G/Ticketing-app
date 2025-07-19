# Use a minimal Java 8 JDK image
FROM openjdk:8-jdk-alpine

# Set app directory
WORKDIR /app

# Copy the JAR file (make sure it's built before you run Docker)
COPY target/ticketing-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
