package com.example.SchoolStaffRecrutimentPlatform;

import org.springframework.boot.SpringApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolStaffRecruitmentPlatformApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		SpringApplication.run(SchoolStaffRecruitmentPlatformApplication.class, args);
	}

}
