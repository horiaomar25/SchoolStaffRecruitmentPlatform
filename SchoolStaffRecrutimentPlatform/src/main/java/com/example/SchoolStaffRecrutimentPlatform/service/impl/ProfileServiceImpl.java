package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.Qualifications;
import com.example.SchoolStaffRecrutimentPlatform.entities.WorkHistory;
import com.example.SchoolStaffRecrutimentPlatform.repository.ProfileRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// Singleton Design Pattern with @Service annotation - creating a single instance of a class and reuse it - memory efficient
// Factory Design Pattern - there can be multiple impl classes of one interface
// Service class takes care of the creation of the object
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public String createProfile(ProfileDTO profileDTO) {
         // Adapter Design Pattern  - Service layer takes on DTO but repository takes on the Entity class,so need to adapt the DTO back to
        // Entity so the layers are compatible with each other
        Profile newProfile = new Profile();
        newProfile.setFirstName(profileDTO.getFirstName());
        newProfile.setLastName(profileDTO.getLastName());
        newProfile.setPosition(profileDTO.getPosition());
        newProfile.setProfileDescription(profileDTO.getProfileDescription());

        // Handle dealing with WorkHistory List to create Profile
        // Persist WorkHistory and Qualifications
        List<WorkHistory> workHistories = new ArrayList<>();
        for(WorkHistoryDTO workHistoryDTO: profileDTO.getWorkHistoryDTO()){
            // Entity class
            WorkHistory newWorkHistory = new WorkHistory();

            // Getters and Setters from WorkHistoryDTO that deal with the entity class
            newWorkHistory.setSchoolName(workHistoryDTO.getSchoolName());
            newWorkHistory.setRole(workHistoryDTO.getRole());
            newWorkHistory.setDuration(workHistoryDTO.getDuration());

            newWorkHistory.setProfile(newProfile);
            workHistories.add(newWorkHistory);

        }

        newProfile.setWorkHistories(workHistories); // Saving List of WorkHistory to the ProfileEntity


        // Handle dealing with Qualification List to create Qualification Entity(DB)
        List<Qualifications> qualifications = new ArrayList<>();
        // Loop through each QualificationsDTO in the ProfileDTO's qualifications list
        for(QualificationsDTO qualificationsDTO: profileDTO.getQualificationsDTO()){

            // Create new object from Entity class
            Qualifications newQualifications = new Qualifications();

            // Sets the properties of the Qualification entity using getter and setting from DTO class. Management of data from DTO class
            newQualifications.setQualificationName(qualificationsDTO.getQualificationName());
            newQualifications.setInstitutionName(qualificationsDTO.getInstitutionName());
            newQualifications.setYearObtained(qualificationsDTO.getYearObtained());

            // Qualifications entity is linked to the Profile entity using the setProfile method.
            newQualifications.setProfile(newProfile);

            // Add the populated Qualifications entity to the qualifications list
            qualifications.add(newQualifications);
        }

        newProfile.setQualifications(qualifications); // Saving List of Qualification to the ProfileEntity
        // repository layer is taking in the Entity class with the data being transferred through the DTO class.
        profileRepository.save(newProfile);

        return "Profile created successfully";
    }
}
