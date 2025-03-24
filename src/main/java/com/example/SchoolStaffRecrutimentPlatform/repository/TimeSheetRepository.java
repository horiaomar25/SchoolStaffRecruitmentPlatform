package com.example.SchoolStaffRecrutimentPlatform.repository;

import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
    // Used to fetch the timesheet according the assignmentId it was created for
    TimeSheet findByAssignmentId(int assignmentId);
}
