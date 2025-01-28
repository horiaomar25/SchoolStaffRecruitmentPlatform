package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    // Post
    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody ProfileDTO profileDTO) {
        String response = profileService.createProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Get

    // Update

    // Response Entity return data and http status.


}
