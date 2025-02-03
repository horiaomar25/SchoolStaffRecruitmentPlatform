package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
// find the Profile by AppUser id (user_id)
}
