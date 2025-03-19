# Stage 1: Build the application with Maven
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/SchoolStaffRecrutimentPlatform-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]