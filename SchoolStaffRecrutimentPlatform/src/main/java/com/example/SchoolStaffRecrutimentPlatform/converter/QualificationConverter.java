package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.Qualifications;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QualificationConverter {
     // Converts single DTO obj to Entity obj
    public Qualifications convertDTOToEntity(QualificationsDTO qualificationsDTO, Profile profile) {
        Qualifications qualifications = new Qualifications();

            qualifications.setQualificationName(qualificationsDTO.getQualificationName());
            qualifications.setInstitutionName(qualificationsDTO.getInstitutionName());
            qualifications.setYearObtained(qualificationsDTO.getYearObtained());
            qualifications.setProfile(profile);


        return qualifications;
    }


    // Convert a DTO List of obj of in Entity List
    public List<Qualifications> convertDTOListToEntityList(List<QualificationsDTO> qualificationsDtoList, Profile profile){
        // for each dto, convert it into an entity which will be added/collected into a List
        // Collectors creates an Array List by default
        return qualificationsDtoList.stream().map(dto-> convertDTOToEntity(dto, profile)).collect(Collectors.toList());

    }
}
