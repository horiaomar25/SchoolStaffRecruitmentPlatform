package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<AppUser, Integer> {
}
