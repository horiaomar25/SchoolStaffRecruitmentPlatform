package com.example.SchoolStaffRecrutimentPlatform.service.impl;

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



    @Transactional
    @Override
    public String createProfile(ProfileDTO profileDTO) {
        // User id to associated with Profile
        Optional<AppUser> appUserOptional = appUserRepo.findById(profileDTO.getAppUserId());

        if (appUserOptional.isEmpty()) {
            return "User not found";
        }

        AppUser appUser = appUserOptional.get();


        // Adapter Design Pattern  - Service layer takes on DTO but repository takes on the Entity class,so need to adapt the DTO back to

        Profile newProfile = new Profile();

        newProfile.setFirstName(profileDTO.getFirstName());
        newProfile.setLastName(profileDTO.getLastName());
        newProfile.setPosition(profileDTO.getPosition());
        newProfile.setProfileDescription(profileDTO.getProfileDescription());

        newProfile.setAppUser(appUser);

        String createWorkHistory = workHistoryService.addWorkHistory(profileDTO.getWorkHistory(), newProfile);

        // Using Qualification Service and calling it in here for better readability.
        // Passing in the DTO List and the Profile entity
        String createQualifications = qualificationService.addQualification(profileDTO.getQualifications(), newProfile);

        profileRepo.save(newProfile);

        return "Profile created successfully";
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

        return "Profile updated successfully";

    }

    public ProfileDTO getProfile(int id) {

        Optional<Profile> findProfile = profileRepo.findById(id);

        Profile profile = findProfile.get();

        // Mapping the Entity back to a DTO
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setPosition(profile.getPosition());
        profileDTO.setProfileDescription(profile.getProfileDescription());

        List<QualificationsDTO> qualificationsDTOList = new ArrayList<>();
        for (Qualifications qualification : profile.getQualifications()) {
            QualificationsDTO qualificationsDTO = new QualificationsDTO();
            qualificationsDTO.setId(qualification.getId());
            qualificationsDTO.setQualificationName(qualification.getQualificationName());
            qualificationsDTO.setInstitutionName(qualification.getInstitutionName());
            qualificationsDTO.setYearObtained(qualification.getYearObtained());
            qualificationsDTOList.add(qualificationsDTO);
        }
        profileDTO.setQualifications(qualificationsDTOList);

        // Mapping the Entity back to a DTO
        List<WorkHistoryDTO> workHistoryDTOList = new ArrayList<>();
       for(WorkHistory workHistory: profile.getWorkHistories()){
           WorkHistoryDTO workHistoryDTO = new WorkHistoryDTO();
           workHistoryDTO.setId(workHistory.getId());

           workHistoryDTO.setRole(workHistory.getRole());
           workHistoryDTO.setDuration(workHistory.getDuration());
           workHistoryDTO.setSchoolId(workHistory.getSchool().getId()); // get school_id
           workHistoryDTO.setProfileId(profile.getId());
           workHistoryDTOList.add(workHistoryDTO);

           // Add school details to WorkHistoryDTO
           School school = workHistory.getSchool();
           if (school != null) {
               SchoolDTO schoolDTO = new SchoolDTO();
               schoolDTO.setId(school.getId());
               schoolDTO.setSchoolName(school.getSchoolName());
               schoolDTO.setSchoolAddress(school.getSchoolAddress());
               workHistoryDTO.setSchool(schoolDTO);
           }
       }

       profileDTO.setWorkHistory(workHistoryDTOList);

        return profileDTO;
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

