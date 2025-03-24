# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar SchoolStaffRecrutimentPlatform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SchoolStaffRecruitmentPlatform.jar"]