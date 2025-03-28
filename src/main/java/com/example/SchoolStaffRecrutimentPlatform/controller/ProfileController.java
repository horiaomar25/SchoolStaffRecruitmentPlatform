package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AppUserRepository appUserRepository;

    // Create a profile. UserId is needed
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO)  {
        try{
            ProfileDTO response = profileService.createProfile(profileDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (UserNotFoundException exception ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    // Will fetch profile data according to authenticated user
    @GetMapping("/personal")
    public ResponseEntity<ProfileDTO> getProfile(Principal principal) {
        // Principal represent authenicated user
        String username = principal.getName();

        AppUser appUser = appUserRepository.findByUsername(username); // find the user in the database

        // get the profile through the user_id fk associated with the profile table
        ProfileDTO profileDTO = profileService.getProfileById(appUser.getId()); // get the user_id associated with appUser entity found by Username.

        return ResponseEntity.ok(profileDTO);
        
    }

    // Update Profile Entity. Only profile description can be updated at this time.
    @PatchMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) {

        ProfileDTO response = profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // Can delete profile via profileId
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        String response = profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
