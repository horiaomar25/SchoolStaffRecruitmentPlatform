package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.converter.ProfileConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.*;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.ProfileNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.*;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;





// Service class takes care of the creation of the object
@Service
public class ProfileServiceImpl implements ProfileService {

    // All dependicies injected into the Service
    @Autowired
    private ProfileRepository profileRepo;


    @Autowired
    private AppUserRepository appUserRepo;

    @Autowired
    private ProfileConverter profileConverter;


   // Takes DTO  from the Controller layer and transforms that data through the converters
    @Transactional
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) throws UserNotFoundException {
        // Finds the user
        Optional<AppUser> appUserOptional = appUserRepo.findById(profileDTO.getAppUserId());


        if(appUserOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        // Converts the DTO to Entity including Qualifications and WorkHistory
        Profile newProfile = profileConverter.convertDTOToEntity(profileDTO);
        AppUser appUser = appUserOptional.get();
        newProfile.setAppUser(appUser);

        // Saving complete profile entity  to database
        Profile savedProfile = profileRepo.save(newProfile);

        // Converting back to DTO to sent to client
        return profileConverter.convertEntityToDTO(savedProfile);


    }

    // Method to assist finding the entity that holding Profile data through the user_id associated with it.
    // Return DTO to be sent back to the client in a GET request
    @Override
    public ProfileDTO getProfileById(int appUserId) {
        // find Profile by the FK of the user associated with the Profile table
        Optional<Profile> findProfile = profileRepo.findById(appUserId);

        if (findProfile.isEmpty()) {
            throw new ProfileNotFoundException("Profile not found");
        }

        Profile profile = findProfile.get();


        return profileConverter.convertEntityToDTO(profile);

    }




    @Transactional
    @Override
    public String deleteProfile(int id) {
        // using repository methods return an Optional<Profile>
        // Optional does not return null, it either contains a value or is empty
        Optional<Profile> findProfile = profileRepo.findById(id);

        // check if there is a value using .isPresent()
        if (findProfile.isPresent()) {
            Profile profile = findProfile.get(); // unwraps the object
            profileRepo.delete(profile);

            return "Profile deleted successfully";

        } else {
            return "Profile not found";
        }
    }

   // Gets the existing profile id and its data. Using the converter to update fields and send back to the client
    @Transactional
    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {

        // Get Profile object for update
        Optional<Profile> updateProfile = profileRepo.findById(profileDTO.getId());

        if (updateProfile.isEmpty()) {
            throw new ProfileNotFoundException("Profile not found");
        }


        // update fields in Profile Entity
        Profile existingProfile = updateProfile.get();


        Profile updatedProfile = profileConverter.convertDTOToEntityForUpdate(profileDTO, existingProfile);

        Profile savedProfile = profileRepo.save(updatedProfile);

        return profileConverter.convertEntityToDTO(savedProfile);

    }





}