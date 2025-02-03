package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {

    @Autowired
    AppUserRepository appUserRepository;

    public Profile convertDTOToEntity(ProfileDTO dto){

        Profile profile = new Profile();

        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setProfileDescription(dto.getProfileDescription());
        profile.setPosition(dto.getPosition());

        if(dto.getAppUserId() != null){

        }


        return profile;

    }

    public ProfileDTO convertEntityToDTO(Profile profile){
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setProfileDescription(profile.getProfileDescription());
        profileDTO.setPosition(profile.getPosition());
        return profileDTO;

    }
}
