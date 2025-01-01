package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.model.Qualifications;
import com.example.SchoolStaffRecrutimentPlatform.model.Users;  // Import Users entity
import com.example.SchoolStaffRecrutimentPlatform.repository.QualificationRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;  // Import Users repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/qualifications")
public class QualificationController {

    private final QualificationRepository qualificationRepository;
    private final UserRepository usersRepository;  // Add a dependency to the UsersRepository

    @Autowired
    public QualificationController(QualificationRepository qualificationRepository, UserRepository userRepository) {
        this.qualificationRepository = qualificationRepository;
        this.usersRepository = userRepository;  // Initialize UsersRepository
    }

    // GET request to get all qualifications
    @GetMapping
    public List<Qualifications> getAllQualifications() {
        return qualificationRepository.findAll();
    }

    // Get Qualification by id
    @GetMapping("/{id}")
    public ResponseEntity<Qualifications> getQualificationById(@PathVariable Long id) {
        Qualifications qualification = qualificationRepository.findById(id).orElse(null);
        if (qualification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(qualification);
    }

    @PostMapping
    public ResponseEntity<?> createQualification(@RequestBody QualificationsDTO qualificationsDTO) {
        if (qualificationsDTO.getUsersId() == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        Users user = usersRepository.findById(qualificationsDTO.getUsersId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        Qualifications qualification = new Qualifications();
        qualification.setUsers(user);
        qualification.setQualificationName(qualificationsDTO.getQualificationName());
        qualification.setResult(qualificationsDTO.getResult());
        qualificationRepository.save(qualification);

        Qualifications savedQualification = qualificationRepository.save(qualification);
        return ResponseEntity.ok(savedQualification);

    }


    @PutMapping("/{id}")
    public ResponseEntity<Qualifications> updateQualification(@PathVariable Long id, @RequestBody Qualifications qualificationRequest) {
        Qualifications existingQualification = qualificationRepository.findById(id).get();

        if (existingQualification == null) {
            return ResponseEntity.status(404).body(null);
        }

        if(qualificationRequest.getUsers() == null || qualificationRequest.getUsers().getUsersId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // request the user
        Users user = usersRepository.findById(qualificationRequest.getUsers().getUsersId()).get();
        if(user == null) {
            return ResponseEntity.status(404).body(null);
        }

        existingQualification.setQualificationName(qualificationRequest.getQualificationName());
        existingQualification.setResult(qualificationRequest.getResult());
        existingQualification.setUsers(user);

        Qualifications updatedQualification = qualificationRepository.save(existingQualification);
        return ResponseEntity.ok(updatedQualification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualification(@PathVariable Long id) {
        Qualifications qualification = qualificationRepository.findById(id).get();
        if(qualification == null) {
            return ResponseEntity.status(404).body("Qualification not found");
        }

        qualificationRepository.deleteById(id);
        return ResponseEntity.ok("Qualification deleted successfully");

    }

}

