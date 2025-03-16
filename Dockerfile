# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src /app/src  # This copies the entire src directory, including src/main/resources

# Set execution permission for Maven Wrapper
RUN chmod +x ./mvnw

# Build the application (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application using OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
