package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssignmentServiceImpl {

    // Inject repositories for User, School, Timesheet and Assignment
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    TimeSheetRepository timeSheetRepository;

    @Autowired
    AppUserRepository appUserRepository;

   public TimeSheet createTimeSheet(int assignmentId) {
       // Find Assignment by Id
       Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
       if (assignmentOpt.isEmpty()) {
           throw new NoSuchElementException("Assignment not found");
       }

       Assignment assignment = assignmentOpt.get();

       TimeSheet timeSheet = new TimeSheet();
      timeSheet.setStartDate(assignment.getStartDate());
      timeSheet.setEndDate(assignment.getEndDate());

   }


}
