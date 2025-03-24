# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /app/target/SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar"]