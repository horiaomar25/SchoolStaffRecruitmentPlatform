package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
}
