
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

# Set execution permission for Maven Wrapper
RUN chmod +x ./mvnw


RUN ./mvnw clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app.jar"]
