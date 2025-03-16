# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Copy source code and resources separately
COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources

# Set execution permissions for the Maven wrapper
RUN chmod +x ./mvnw

# Build the project without running tests
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application using OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Set environment variables (optional, can be overridden by Render)
ENV SPRING_PROFILES_ACTIVE=prod

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
