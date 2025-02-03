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


    // Convert a single Entity obj into a dto obj
    public QualificationsDTO convertEntityToDTO(Qualifications qualifications) {
        QualificationsDTO qualificationsDTO = new QualificationsDTO();
        qualificationsDTO.setQualificationName(qualifications.getQualificationName());
        qualificationsDTO.setInstitutionName(qualifications.getInstitutionName());
        qualificationsDTO.setYearObtained(qualifications.getYearObtained());
        return qualificationsDTO;
    }

public List<QualificationsDTO> convertListToDTO(List<Qualifications> qualificationsList) {
        return qualificationsList.stream().map(qualifications -> convertEntityToDTO(qualifications)).collect(Collectors.toList());


    }
}
