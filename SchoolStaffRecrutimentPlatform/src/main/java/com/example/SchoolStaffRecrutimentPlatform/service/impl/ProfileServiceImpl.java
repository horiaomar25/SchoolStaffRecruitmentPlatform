package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.repository.ProfileRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Singleton Design Pattern with @Service annotation - creating a single instance of a class and reuse it - memory efficient
// Factory Design Pattern - there can be multiple impl classes of one interface
// Service class takes care of the creation of the object
@Service
public class ProfileServiceImpl implements ProfileService {


    private ProfileRepository profileRepository;


    @Override
    public String createProfile(ProfileDTO profileDTO) {
        Profile newProfile = new Profile();
        newProfile.setFirstName(profileDTO.getFirstName());
        newProfile.setLastName(profileDTO.getLastName());
        newProfile.setPosition(profileDTO.getPosition());
        newProfile.setProfileDescription(profileDTO.getProfileDescription());

        // Handle dealing with WorkHistory List to create Profile

        // Handle dealing with Qualification List to create Qualification

    }
}
