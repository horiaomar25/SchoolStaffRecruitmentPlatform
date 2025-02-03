package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;
    @Autowired
    private AppUserRepository appUserRepository;

    // Post
    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody ProfileDTO profileDTO) {
        String response = profileService.createProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    // Will fetch profile data according to authenticated user
    @GetMapping("/personal")
    public ResponseEntity<ProfileDTO> geProfile(Principal principal) {
        // Principal represent authenicated user
        String username = principal.getName();

        AppUser appUser = appUserRepository.findByUsername(username); // find the user in the database

        // get the profile through the user_id fk associated with the profile table
        ProfileDTO profileDTO = profileService.getProfileById(appUser.getId()); // get the user_id associated with appUser entity found by Username.

        return ResponseEntity.ok(profileDTO);
        
    }




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        String response = profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    // Update Profile Entity including Qualification/WorkHistory
    @PutMapping("/update")
    public ResponseEntity<String> updateProfile( @RequestBody ProfileDTO profileDTO) {

        String response = profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

//    @PutMapping("/{id}/workhistory")
//    public ResponseEntity<String> updateProfileWorkHistory(@PathVariable int id, @RequestBody ProfileDTO profileDTO) {
//        // set id
//        profileDTO.setId(id);
//        String response = profileService.updateWorkHistory(profileDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//
//    }
//
//    @PutMapping("/{id}/qualifications")
//    public ResponseEntity<String> updateProfileQualifications(@PathVariable int id, @RequestBody ProfileDTO profileDTO) {
//        profileDTO.setId(id);
//        String response = profileService.updateQualification(profileDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//
//    }






}
