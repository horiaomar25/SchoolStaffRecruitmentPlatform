# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY SchoolStaffRecrutimentPlatform/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app /app.jar"]
EXPOSE 8080