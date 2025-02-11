package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.converter.AssignmentConverter;
import com.example.SchoolStaffRecrutimentPlatform.converter.TimeSheetConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;

import com.example.SchoolStaffRecrutimentPlatform.exceptions.AssignmentNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
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
    TimeSheetRepository timeSheetRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    TimeSheetConverter timeSheetConverter;

    @Autowired
    AssignmentConverter assignmentConverter;

    // Find the assignment the user has selected and assign the userId to that assignment so it can be added to the dashboard
   public Assignment acceptAssignment (int appUserId, int assignmentId) throws UserNotFoundException {
       // Find the assignment
       Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);

       // Find User
       Optional<AppUser> appUserOpt = appUserRepository.findById(appUserId);

       if(assignmentOpt.isEmpty()){
           throw new AssignmentNotFoundException("Assignment not found");
       }

       if(appUserOpt.isEmpty()){
           throw new UserNotFoundException("User not found");
       }

       // Unwraps objects so it can be used
       AppUser appUser = appUserOpt.get();

       Assignment assignment = assignmentOpt.get();

       assignment.setUser(appUser);

       return assignmentRepository.save(assignment);


   }


   public TimeSheetDTO createTimeSheet(int assignmentId) throws UserNotFoundException {
       // Find Assignment by Id
       Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);

       if (assignmentOpt.isEmpty()) {
           throw new NoSuchElementException("Assignment not found");
       }

       Assignment assignment = assignmentOpt.get(); // unwraps assignment

       // Create timesheet based on assignment details
       TimeSheet timeSheet = new TimeSheet();
       timeSheet.setStartDate(assignment.getStartDate());
       timeSheet.setEndDate(assignment.getEndDate());

       timeSheet.setAssignment(assignment);

       // Fetch the User associated with the Assignment
       AppUser user = assignment.getUser();  // Assuming getUser() returns the associated user
       if (user == null) {
           throw new UserNotFoundException("User not found for the given assignment");
       }

       timeSheet.createAllDates(); // uses the startDate and endDate to get all date in between for the timesheet

       // set the user
       timeSheet.setUser(user);


       TimeSheet savedTimeSheet = timeSheetRepository.save(timeSheet);

       return timeSheetConverter.convertTimeSheetToTimeSheetDTO(savedTimeSheet);

   }


    public AssignmentDTO getAcceptedAssignment(int appUserId) throws UserNotFoundException {
        // Find the user by ID
        Optional<AppUser> appUserOpt = appUserRepository.findById(appUserId);

        if (appUserOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }


        AppUser appUser = appUserOpt.get();

        // Find the assignment by user ID
        Assignment assignment = assignmentRepository.findByUser(appUser);

        if (assignment == null) {
            throw new AssignmentNotFoundException("No assignment found for this user");
        }




        AssignmentDTO assignmentDTO = assignmentConverter.convertEntityToDto(assignment);


        return assignmentDTO;
    }



}
