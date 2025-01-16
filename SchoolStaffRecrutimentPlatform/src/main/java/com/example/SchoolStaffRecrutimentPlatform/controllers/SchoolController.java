package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.entities.School;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;


import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    // GET request to get all schools
    @GetMapping
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    // GET request to get a specific school by ID
    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
       School school = schoolRepository.findById(id).get();
       if (school == null) {
           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(school);
    }

    // POST request to create a new school
    @PostMapping
    public School createSchool(@RequestBody School school) {
        return schoolRepository.save(school);
    }



    // DELETE request to delete a school by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        School school = schoolRepository.findById(id).get();
        if (school == null) {
            return ResponseEntity.notFound().build();
        }

       schoolRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }


}
