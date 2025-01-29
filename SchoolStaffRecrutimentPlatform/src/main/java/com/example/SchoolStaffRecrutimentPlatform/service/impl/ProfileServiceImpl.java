package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.*;
import com.example.SchoolStaffRecrutimentPlatform.repository.*;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
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
    private WorkHistoryRepository workHistoryRepo;

    @Autowired
    private AppUserRepository appUserRepo;

    @Autowired
    private SchoolRepository schoolRepository;


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

        // Handle dealing with WorkHistory List to create Profile
        // Persist WorkHistory and Qualifications
        List<WorkHistory> workHistories = new ArrayList<>();

        for (WorkHistoryDTO workHistoryDTO : profileDTO.getWorkHistory()) {
            // Fetch School Entity based on ID
            Optional<School> schoolOptional = schoolRepository.findById(workHistoryDTO.getSchoolId());
            if (schoolOptional.isPresent()) {
                School school = schoolOptional.get();

                // Entity class
                WorkHistory newWorkHistory = new WorkHistory();

                // Getters and Setters from WorkHistoryDTO that deal with the entity class
                newWorkHistory.setSchoolName(workHistoryDTO.getSchoolName());
                newWorkHistory.setRole(workHistoryDTO.getRole());
                newWorkHistory.setDuration(workHistoryDTO.getDuration());
                newWorkHistory.setSchool(school);

                newWorkHistory.setProfile(newProfile);
                workHistories.add(newWorkHistory);
            }


        }

        newProfile.setWorkHistories(workHistories); // Saving List of WorkHistory to the ProfileEntity

        // Using Qualification Service and calling it in here for better readability.
        // Passing in the DTO List and the Profile entity
        String createQualifications = qualificationService.addQualification(profileDTO.getQualifications(), newProfile);

        profileRepo.save(newProfile);

        return "Profile created successfully";
    }

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

    @Override
    public String updateQualification(ProfileDTO profileDTO) {
        Optional<Profile> existingProfileOpt = profileRepo.findById(profileDTO.getId());

        if (existingProfileOpt.isEmpty()) {
            return "Profile not found";
        }

        Profile existingProfile = existingProfileOpt.get();


        //update Qualification List - same structure as the WorkHistory
        for (QualificationsDTO qualificationsDTO : profileDTO.getQualifications()) {
            // Fetch the qualification using its ID
            Optional<Qualifications> existingQualificationOpt = qualificationsRepo.findById(qualificationsDTO.getId());

            if (existingQualificationOpt.isPresent()) {
                // Retrieve the qualification from the Optional
                Qualifications existingQualification = existingQualificationOpt.get();

                // Update its fields
                existingQualification.setQualificationName(qualificationsDTO.getQualificationName());
                existingQualification.setInstitutionName(qualificationsDTO.getInstitutionName());
                existingQualification.setYearObtained(qualificationsDTO.getYearObtained());
                existingQualification.setProfile(existingProfile);

                // Save the updated qualification entity
                qualificationsRepo.save(existingQualification);
            } else {
                // Handle case where qualification is not found (log, throw exception, etc.)
                System.out.println("Qualification with ID " + qualificationsDTO.getId() + " not found.");
            }
        }

        return "Qualifications updated successfully";
    }



    @Override
    public String updateWorkHistory(ProfileDTO profileDTO) {
        Optional<Profile> profileOptional = profileRepo.findById(profileDTO.getId());

        if (profileOptional.isEmpty()) {  // FIXED variable name
            return "Profile not found";
        }

        Profile currentExistingProfile = profileOptional.get();

        // Update WorkHistory List
        for (WorkHistoryDTO workHistoryDTO : profileDTO.getWorkHistory()) {
            Optional<WorkHistory> workHistoryOpt = workHistoryRepo.findById(workHistoryDTO.getId());

            if (workHistoryOpt.isPresent()) {
                WorkHistory existingWorkHistory = workHistoryOpt.get();

                existingWorkHistory.setSchoolName(workHistoryDTO.getSchoolName());
                existingWorkHistory.setRole(workHistoryDTO.getRole());
                existingWorkHistory.setDuration(workHistoryDTO.getDuration());
                existingWorkHistory.setProfile(currentExistingProfile); // FIXED variable name

                workHistoryRepo.save(existingWorkHistory); // Saves changes to the DB
            } else {
                System.out.println("Work history with ID " + workHistoryDTO.getId() + " not found.");
            }
        }

        return "Work History updated successfully";
    }

}

