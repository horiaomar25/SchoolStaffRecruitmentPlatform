FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app/SchoolStaffRecruitmentPlatform
COPY SchoolStaffRecruitmentPlatform/ ./
RUN ls -l # Check if pom.xml is present
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/SchoolStaffRecruitmentPlatform/target/SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]