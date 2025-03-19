FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/SchoolStaffRecruitmentPlatform-0.0.1-SNAPSHOT.jar SchoolStaffRecruitmentPlatform.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "SchoolStaffRecruitmentPlatform.jar"]