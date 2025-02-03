package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.Qualifications;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QualificationConverter {

    public Qualifications convertDTOToEntity(QualificationsDTO qualificationsDTO, Profile profile) {
        Qualifications qualifications = new Qualifications();

            qualifications.setQualificationName(qualificationsDTO.getQualificationName());
            qualifications.setInstitutionName(qualificationsDTO.getInstitutionName());
            qualifications.setYearObtained(qualificationsDTO.getYearObtained());
            qualifications.setProfile(profile);


        return qualifications;
    }


    // Convert a DTO List of in Entity List
    public List<Qualifications> convertDTOListToEntityList(List<QualificationsDTO> dtoList, Profile profile){
        // mapping a List to List
        // using convertDTOToEntity method to convert each DTO to a entity and then addding it to a List
        // which will be returned.


    }
}
