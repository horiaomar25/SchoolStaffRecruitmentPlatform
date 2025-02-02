package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    // Get all unassigned
    @GetMapping("/unassigned")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> unassignedAssignment = assignmentRepository.findByUserIsNull();
        if (unassignedAssignment.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(unassignedAssignment);
    }
}
