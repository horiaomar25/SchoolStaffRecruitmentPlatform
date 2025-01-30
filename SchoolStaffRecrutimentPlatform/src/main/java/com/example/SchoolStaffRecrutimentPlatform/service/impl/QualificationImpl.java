package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.Qualifications;

import com.example.SchoolStaffRecrutimentPlatform.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QualificationImpl {

    // Repository gives access to data
    @Autowired
    QualificationRepository qualificationRepo;


    // DTO carries the incoming request from the client
    // Purpose: Mapping DTO to entity
    public String addQualification(List<QualificationsDTO> qualificationsDTOList, Profile profile) {
        // Represents a List of Qualification Entities
        List<Qualifications> newqualificationsList = new ArrayList<>();

        // Looping through DTO and adding them to Entity to be saved to the db
        for (QualificationsDTO dto : qualificationsDTOList) {
            // Create new qualification entity from DTO
            Qualifications qualification = new Qualifications();

            // Set the properties of the entity based on the DTO
            qualification.setQualificationName(dto.getQualificationName());
            qualification.setInstitutionName(dto.getInstitutionName());
            qualification.setYearObtained(dto.getYearObtained());
            qualification.setProfile(profile); // Set the profile of the user to the qualification

            // Add the entity to the list that will then be saved to the DB
            newqualificationsList.add(qualification);
        }

        // Save all qualifications to the DB in a single batch
        qualificationRepo.saveAll(newqualificationsList);
        return "Qualification created.";
    }

    // Update Qualification
    // This method updates the qualifications list for the specified profile
    public String updateQualification(List<QualificationsDTO> qualificationsDTOList, Profile profile) {
        // List to hold updated qualifications entities
        List<Qualifications> updatedQualificationsList = new ArrayList<>();

        // Loop through each DTO and map it to an entity
        for (QualificationsDTO dto : qualificationsDTOList) {

            Optional<Qualifications> existingQualification = qualificationRepo.findById(dto.getId());

            if(existingQualification.isEmpty()) {
                return "Qualification not found.";
            }

            Qualifications qualifications = existingQualification.get();

            // Set the properties of the entity based on the DTO
            qualifications.setQualificationName(dto.getQualificationName());
            qualifications.setInstitutionName(dto.getInstitutionName());
            qualifications.setYearObtained(dto.getYearObtained());
            qualifications.setProfile(profile); // Set the profile of the user to the qualification

            // Add the updated qualification to the list
            updatedQualificationsList.add(qualifications);
        }


        qualificationRepo.saveAll(updatedQualificationsList);


        return "Qualifications updated successfully";
    }
}
