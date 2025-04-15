package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<ProfileDTO> getProfile(HttpServletRequest request) {


        String token = getTokenFromHeader(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProfileDTO profileDTO = profileService.getProfileById(appUser.getId());

        return ResponseEntity.ok(profileDTO);

        
    }

    // Update Profile Entity. Only profile description can be updated at this time.
    @PatchMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(HttpServletRequest request, @RequestBody ProfileDTO profileDTO) {

        String token = getTokenFromHeader(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProfileDTO response = profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    // Can delete profile via profileId
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        String response = profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // Helper method to extract token from cookie
    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }

        return null;
    }


}
