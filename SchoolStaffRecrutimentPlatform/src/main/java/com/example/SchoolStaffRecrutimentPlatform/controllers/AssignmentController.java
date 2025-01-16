package com.example.SchoolStaffRecrutimentPlatform.controllers;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.model.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.model.School;
import com.example.SchoolStaffRecrutimentPlatform.model.Users;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    // Repository has built in methods to use such as findByID
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    // Repositories needed have to be injected into Controller so you can make use of built in methods
    @Autowired
    public AssignmentController(AssignmentRepository assignmentRepository, UserRepository userRepository, SchoolRepository schoolRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {

        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok(assignment);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {

        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return ResponseEntity.status(404).body("Assignment not found");
        }

        assignmentRepository.deleteById(id);
        return ResponseEntity.ok("Assignment deleted successfully");
    }
}