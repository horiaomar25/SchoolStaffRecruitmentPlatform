package com.example.SchoolStaffRecrutimentPlatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SchoolStaffRecrutimentPlatform.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
