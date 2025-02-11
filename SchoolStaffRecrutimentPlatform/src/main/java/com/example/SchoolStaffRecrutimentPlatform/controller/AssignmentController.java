package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.converter.TimeSheetConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;

import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentServiceImpl assignmentService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TimeSheetRepository timeSheetRepository;
    @Autowired
    private TimeSheetConverter timeSheetConverter;


    // Get all unassigned
    @GetMapping("/unassigned")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> unassignedAssignment = assignmentRepository.findByUserIsNull();
        if (unassignedAssignment.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(unassignedAssignment);
    }

    // fetch accepted assignment
    @GetMapping("/accepted")
    public ResponseEntity<Assignment> getAcceptedAssignment(Principal principal) {
        String userName = principal.getName();

        AppUser appUser = appUserRepository.findByUsername(userName);

        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

       Assignment assignment = assignmentService.getAcceptedAssignment(appUser.getId());

        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(assignment);
    }

    @PutMapping("/{assignmentId}/accept")
    public ResponseEntity<Assignment> acceptAssignment(@PathVariable int assignmentId, Principal principal) {

        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = principal.getName();


        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Assignment assignment = assignmentService.acceptAssignment(appUser.getId(), assignmentId);
        return ResponseEntity.ok(assignment);
    }



    @PostMapping("/{assignmentId}/timesheet")
    public ResponseEntity<TimeSheetDTO> createTimeSheet(@PathVariable int assignmentId) {

        TimeSheetDTO response = assignmentService.createTimeSheet(assignmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{assignmentId}/gettimesheet")
    public ResponseEntity<TimeSheetDTO> getTimeSheetByAssignmentId(@PathVariable int assignmentId) {

        TimeSheet timeSheet = timeSheetRepository.findByAssignmentId(assignmentId);

        if (timeSheet == null) {
            return ResponseEntity.noContent().build();
        }

        TimeSheetDTO timeSheetDTO = timeSheetConverter.convertTimeSheetToTimeSheetDTO(timeSheet);

        return ResponseEntity.ok(timeSheetDTO);

    }
}
