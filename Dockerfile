

FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /SchoolStaffRecruitmentPlatform


COPY SchoolStaffRecruitmentPlatform/pom.xml .
COPY SchoolStaffRecruitmentPlatform/.mvn .mvn
COPY SchoolStaffRecruitmentPlatform/mvnw .
COPY SchoolStaffRecruitmentPlatform/src src


RUN chmod +x ./mvnw


RUN ./mvnw clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /SchoolStaffRecruitmentPlatform  # Change WORKDIR in Stage 2 as well


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
