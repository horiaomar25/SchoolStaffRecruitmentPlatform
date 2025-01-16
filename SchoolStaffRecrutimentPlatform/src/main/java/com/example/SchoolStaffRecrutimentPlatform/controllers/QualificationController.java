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

        // Check by user id for the qualification associated with the user or return null
        Users user = usersRepository.findById(qualificationsDTO.getUsersId()).orElse(null);

        // if it is null return http 404 with message "User not found"
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Create a new object with the data saved into it.
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

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualification(@PathVariable Long id) {
        Qualifications qualification = qualificationRepository.findById(id).get();



        qualificationRepository.deleteById(id);
        return ResponseEntity.ok("Qualification deleted successfully");

    }

}

