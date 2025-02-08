package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // Find the assignment the user has selected and assign the userId to that assignment so it can be added to the dashboard
   public Assignment acceptAssignment (int appUserId, int assignmentId) {
       // Find the assignment
       Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);

       // Find User
       Optional<AppUser> appUserOpt = appUserRepository.findById(appUserId);

       if(assignmentOpt.isEmpty()){
           throw new NoSuchElementException("Assignment not found");
       }

       if(appUserOpt.isEmpty()){
           throw new NoSuchElementException("User not found");
       }
       // Unwraps objects so it can be used
       AppUser appUser = appUserOpt.get();
       Assignment assignment = assignmentOpt.get();

       assignment.setUser(appUser);

       return assignmentRepository.save(assignment);


   }

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

       timeSheet.setAssignment(assignment);
       timeSheet.setUser(assignment.getUser());
       timeSheet.setSchool(assignment.getSchool());

       timeSheet.createAllDates();

       return timeSheetRepository.save(timeSheet);

   }
    public Assignment getAcceptedAssignment(int appUserId) {
        // Find the user by ID
        Optional<AppUser> appUserOpt = appUserRepository.findById(appUserId);

        if (appUserOpt.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        // Get the user (no need to re-assign appUserId)
        AppUser appUser = appUserOpt.get();

        // Find the assignment by user ID
        Assignment assignment = assignmentRepository.findByUser(appUser);

        if (assignment == null) {
            throw new NoSuchElementException("No assignment found for this user");
        }

        return assignment;
    }



}
