package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.converter.ProfileConverter;
import com.example.SchoolStaffRecrutimentPlatform.converter.QualificationConverter;
import com.example.SchoolStaffRecrutimentPlatform.converter.WorkHistoryConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;

import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.SchoolDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.*;
import com.example.SchoolStaffRecrutimentPlatform.repository.*;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


// Singleton Design Pattern with @Service annotation - creating a single instance of a class and reuse it - memory efficient
// Factory Design Pattern - there can be multiple impl classes of one interface
// Service class takes care of the creation of the object
@Service
public class ProfileServiceImpl implements ProfileService {

   // All dependies injected into the Service
    @Autowired
    private ProfileRepository profileRepo;

   @Autowired
   QualificationImpl qualificationService;

   @Autowired
   WorkHistoryImpl workHistoryService;

    @Autowired
    private AppUserRepository appUserRepo;

    @Autowired
    private ProfileConverter profileConverter;
    @Autowired
    private WorkHistoryConverter workHistoryConverter;
    @Autowired
    private QualificationConverter qualificationConverter;


// Adapter Design Pattern  - Service layer takes on DTO but repository takes on the Entity class,so need to adapt the DTO back to

    @Transactional
    @Override
    public String createProfile(ProfileDTO profileDTO) {
        // User id to associated with Profile
        Optional<AppUser> appUserOptional = appUserRepo.findById(profileDTO.getAppUserId());

        if (appUserOptional.isEmpty()) {
            return "User not found";
        }

        AppUser appUser = appUserOptional.get();

        Profile newProfile = profileConverter.convertDTOToEntity(profileDTO);
        newProfile.setAppUser(appUser);

        List<WorkHistory> workHistories = workHistoryConverter.convertDTOListToEntity(profileDTO.getWorkHistory(), newProfile);
        newProfile.setWorkHistories(workHistories);

        List<Qualifications> qualifications = qualificationConverter.convertDTOListToEntityList(profileDTO.getQualifications(), newProfile);
        newProfile.setQualifications(qualifications);

        profileRepo.save(newProfile);

        return "Profile created successfully";
    }

    // will get the profile using user_id which is a foriegn key in the profile table
    @Override
    public ProfileDTO getProfileById(int appUserId) {
        // find Profile by the FK of the user associated with the Profile table
        Optional<Profile> findProfile = profileRepo.findById(appUserId);

        if (findProfile.isEmpty()) {
           throw new NoSuchElementException("Profile not found");
        }

         // entity
        Profile profile = findProfile.get();

        ProfileDTO profileDTO = profileConverter.convertEntityToDTO(profile);

        // If no qualifications are found, set an empty list
        List<QualificationsDTO> qualificationsDTOList = qualificationConverter.convertEntityListToDTOList(profile.getQualifications() != null ? profile.getQualifications() : new ArrayList<>());
        profileDTO.setQualifications(qualificationsDTOList);

// Similarly, handle work histories:
        List<WorkHistoryDTO> workHistoryDTOList = workHistoryConverter.convertEntityListToDTOList(profile.getWorkHistories() != null ? profile.getWorkHistories() : new ArrayList<>());
        profileDTO.setWorkHistory(workHistoryDTOList);


        return profileDTO;

    }




    @Transactional
    @Override
    public String deleteProfile(int id) {
        // using repository methods return a Optional<Profile>
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


    @Transactional
    @Override
    public String updateProfile(ProfileDTO profileDTO) {
        // Get user id from Profile DTO
        Optional<AppUser> appUserOptional = appUserRepo.findById(profileDTO.getAppUserId());

        if (appUserOptional.isEmpty()) {
            return "User not found";
        }

        AppUser appUser = appUserOptional.get();

        // Get Profile object for update
        Optional<Profile> updateProfile = profileRepo.findById(profileDTO.getId());

        if (updateProfile.isEmpty()) {
            return "Profile not found";
        }


        // update fields in Profile Entity
        Profile existingProfile = updateProfile.get();
        existingProfile.setFirstName(profileDTO.getFirstName());
        existingProfile.setLastName(profileDTO.getLastName());
        existingProfile.setPosition(profileDTO.getPosition());
        existingProfile.setProfileDescription(profileDTO.getProfileDescription());
        existingProfile.setAppUser(appUser);

        profileRepo.save(existingProfile);

        return "Profile updated successfully";

    }


    @Override
    public String updateQualification(ProfileDTO profileDTO) {
        Optional<Profile> existingProfileOpt = profileRepo.findById(profileDTO.getId());

        if (existingProfileOpt.isEmpty()) {
            return "Profile not found";
        }

        Profile existingProfile = existingProfileOpt.get();

        String updateQualificationsResponse = qualificationService.updateQualification(profileDTO.getQualifications(), existingProfile);

        return "Qualifications updated successfully";
    }


    @Override
    public String updateWorkHistory(ProfileDTO profileDTO) {
        Optional<Profile> profileOptional = profileRepo.findById(profileDTO.getId());

        if (profileOptional.isEmpty()) {  // FIXED variable name
            return "Profile not found";
        }

        Profile currentExistingProfile = profileOptional.get();

        String updatedWorkHistoryResponse = workHistoryService.updateWorkHistory(profileDTO.getWorkHistory(), currentExistingProfile);

        return "Work History updated successfully";
    }



}

