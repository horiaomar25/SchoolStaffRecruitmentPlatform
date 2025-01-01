package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.model.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {

}
