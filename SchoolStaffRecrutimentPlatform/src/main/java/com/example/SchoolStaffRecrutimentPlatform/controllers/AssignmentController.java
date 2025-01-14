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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignment);
    }

    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody AssignmentDTO assignmentRequest) {
        if (assignmentRequest.getUsersId() == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }
        if (assignmentRequest.getSchoolId() == null) {
            return ResponseEntity.badRequest().body("School ID is required");
        }

        Users user = userRepository.findById(assignmentRequest.getUsersId()).orElse(null);
        School school = schoolRepository.findById(assignmentRequest.getSchoolId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (school == null) {
            return ResponseEntity.status(404).body("School not found");
        }

        Assignment assignment = new Assignment();
        assignment.setUsers(user);
        assignment.setSchool(school);
        assignment.setRole(assignmentRequest.getRole());
        assignment.setDuration(assignmentRequest.getDuration());
        assignment.setFeedback(assignmentRequest.getFeedback());

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return ResponseEntity.ok(savedAssignment);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignmentRequest) {
        Assignment existingAssignment = assignmentRepository.findById(id).orElse(null);

        if (existingAssignment == null) {
            return ResponseEntity.status(404).build();
        }

        if (assignmentRequest.getUsers() != null && assignmentRequest.getUsers().getUsersId() != null) {
            Users user = userRepository.findById(assignmentRequest.getUsers().getUsersId()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).build();
            }
            existingAssignment.setUsers(user);
        }

        if (assignmentRequest.getSchool() != null && assignmentRequest.getSchool().getSchoolId() != null) {
            School school = schoolRepository.findById(assignmentRequest.getSchool().getSchoolId()).orElse(null);
            if (school == null) {
                return ResponseEntity.status(404).build();
            }
            existingAssignment.setSchool(school);
        }

        existingAssignment.setRole(assignmentRequest.getRole());
        existingAssignment.setDuration(assignmentRequest.getDuration());
        existingAssignment.setFeedback(assignmentRequest.getFeedback());

        Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
        return ResponseEntity.ok(updatedAssignment);
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