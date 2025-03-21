package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Integer> {

}
