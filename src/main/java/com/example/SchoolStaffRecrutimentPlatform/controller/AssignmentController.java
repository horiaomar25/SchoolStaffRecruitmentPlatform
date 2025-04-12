package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.converter.TimeSheetConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;

import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AssignmentServiceImpl;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private JwtUtil jwtUtil;



    // Get all unassigned assignments

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
    public ResponseEntity<AssignmentDTO> getAcceptedAssignment(HttpServletRequest request) throws UserNotFoundException {

        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String userName = jwtUtil.getUsernameFromToken(token);

        AppUser appUser = appUserRepository.findByUsername(userName);

        if(appUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        AssignmentDTO assignmentDTO = assignmentService.getAcceptedAssignment(appUser.getId());

        if(assignmentDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(assignmentDTO);


//        String userName = principal.getName();
//
//        AppUser appUser = appUserRepository.findByUsername(userName);
//
//        if (appUser == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//       AssignmentDTO assignmentDTO = assignmentService.getAcceptedAssignment(appUser.getId());
//
//        if (assignmentDTO == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        return ResponseEntity.ok(assignmentDTO);
    }

    // Allows user to accept assignment
    @PutMapping("/{assignmentId}/accept")
    public ResponseEntity<Assignment> acceptAssignment(@PathVariable int assignmentId, Principal principal) throws UserNotFoundException {

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


   // Creates a timesheet based on the start date and end date and calculates the dates in between using method in timesheet entity
    @PostMapping("/{assignmentId}/timesheet")
    public ResponseEntity<TimeSheetDTO> createTimeSheet(@PathVariable int assignmentId, HttpServletRequest request) {

        String token = getTokenFromHeader(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TimeSheetDTO response = assignmentService.createTimeSheet(assignmentId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);



//        TimeSheetDTO response = assignmentService.createTimeSheet(assignmentId);
//
//        if (response == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(null); // Ensure response is not empty
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // Fetches the created timesheet
    @GetMapping("/{assignmentId}/gettimesheet")
    public ResponseEntity<TimeSheetDTO> getTimeSheetByAssignmentId(@PathVariable int assignmentId,HttpServletRequest request) {

        String token = getTokenFromHeader(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        TimeSheet timeSheet = timeSheetRepository.findByAssignmentId(assignmentId);
        if (timeSheet == null) {
            return ResponseEntity.noContent().build();
        }

        TimeSheetDTO timeSheetDTO = timeSheetConverter.convertTimeSheetToTimeSheetDTO(timeSheet);
        return ResponseEntity.ok(timeSheetDTO);

//        TimeSheet timeSheet = timeSheetRepository.findByAssignmentId(assignmentId);
//
//        if (timeSheet == null) {
//            return ResponseEntity.noContent().build();
//        }
//
//        TimeSheetDTO timeSheetDTO = timeSheetConverter.convertTimeSheetToTimeSheetDTO(timeSheet);
//
//        return ResponseEntity.ok(timeSheetDTO);

    }

    // Helper method to extract token from cookie
    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }

        return null;
    }
}
