FROM openjdk:17-jdk-alpine

#VOLUME /tmp

WORKDIR /

COPY ./SchoolStaffRecrutimentPlatform/target/SchoolStaffRecrutimentPlatform-0.0.1-SNAPSHOT.jar SchoolStaffRecrutimentPlatform.jar


ENTRYPOINT ["java", "-jar", "SchoolStaffRecruitmentPlatform.jar"]