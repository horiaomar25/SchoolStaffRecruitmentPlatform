package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);
    void deleteByUsername(String username);
}
