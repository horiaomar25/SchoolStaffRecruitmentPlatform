package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable int id) {
        try{
            ProfileDTO profileDTO = profileService.getProfile(id);
            return ResponseEntity.status(HttpStatus.OK).body(profileDTO);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        String response = profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    // Update Profile Entity including Qualification/WorkHistory
    @PutMapping("/{id}/profileDetails")
    public ResponseEntity<String> updateProfile(@PathVariable int id, @RequestBody ProfileDTO profileDTO) {
        profileDTO.setId(id);
        String response = profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}/workhistory")
    public ResponseEntity<String> updateProfileWorkHistory(@PathVariable int id, @RequestBody ProfileDTO profileDTO) {
        // set id
        profileDTO.setId(id);
        String response = profileService.updateWorkHistory(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}/qualifications")
    public ResponseEntity<String> updateProfileQualifications(@PathVariable int id, @RequestBody ProfileDTO profileDTO) {
        profileDTO.setId(id);
        String response = profileService.updateQualification(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }






}
