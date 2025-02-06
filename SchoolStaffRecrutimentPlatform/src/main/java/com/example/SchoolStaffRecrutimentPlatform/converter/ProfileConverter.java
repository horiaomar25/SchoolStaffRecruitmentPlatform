package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.SchoolDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.*;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileConverter {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SchoolRepository schoolRepository;

    // Used for Post request
    public Profile convertDTOToEntity(ProfileDTO profileDTO){

        Profile profile = new Profile();

        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setProfileDescription(profileDTO.getProfileDescription());
        profile.setPosition(profileDTO.getPosition());
        AppUser appUser = new AppUser();
        profile.setAppUser(appUser);

        List<WorkHistory> workHistoryEntityList = profileDTO.getWorkHistory().stream()
                .map(workHistory -> {
                    WorkHistory workHistoryEntity = new WorkHistory();
                    workHistoryEntity.setRole(workHistory.getRole());
                    workHistoryEntity.setDuration(workHistory.getDuration());
                    workHistoryEntity.setProfile(profile);

                    School school = schoolRepository.findById(workHistory.getSchoolId()).get();
                    workHistoryEntity.setSchool(school);

                    return workHistoryEntity;
                })
                .collect(Collectors.toList());

        profile.setWorkHistories(workHistoryEntityList);

        List<Qualifications> qualificationEntityList = profileDTO.getQualifications().stream()
                .map(qualifications -> {
                    Qualifications qualificationsEntity = new Qualifications();
                    qualificationsEntity.setId(qualifications.getId());
                    qualificationsEntity.setQualificationName(qualifications.getQualificationName());
                    qualificationsEntity.setInstitutionName(qualifications.getInstitutionName());
                    qualificationsEntity.setYearObtained(qualifications.getYearObtained());
                    qualificationsEntity.setProfile(profile);
                    return qualificationsEntity;
                })
                .collect(Collectors.toList());

        profile.setQualifications(qualificationEntityList);


        return profile;

    }

     // used for the GET request
    // Service layer uses this prepare data for the client
    public ProfileDTO convertEntityToDTO(Profile profile){

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId(profile.getId());
        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setProfileDescription(profile.getProfileDescription());
        profileDTO.setPosition(profile.getPosition());
        profileDTO.setAppUserId(profile.getAppUser().getId());

        List<WorkHistoryDTO> workHistoryDTOList = profile.getWorkHistories().stream()
        .map(workHistory -> {
            WorkHistoryDTO workHistoryDTO = new WorkHistoryDTO();
            workHistoryDTO.setId(workHistory.getId());
            workHistoryDTO.setRole(workHistory.getRole());
            workHistoryDTO.setDuration(workHistory.getDuration());
            workHistoryDTO.setSchoolId(workHistory.getSchool().getId());
            workHistoryDTO.setProfileId(profile.getId());

            // Mapping the School to a Work History entry
            SchoolDTO schoolDTO = new SchoolDTO();
            schoolDTO.setId(workHistory.getSchool().getId());
            schoolDTO.setSchoolName(workHistory.getSchool().getSchoolName());
            schoolDTO.setSchoolAddress(workHistory.getSchool().getSchoolAddress());

            workHistoryDTO.setSchool(schoolDTO);

            return workHistoryDTO;

        })
                .collect(Collectors.toList());

        List<QualificationsDTO> qualificationsDTOList = profile.getQualifications().stream()
                .map(qualifications -> {
                    QualificationsDTO qualificationsDTO = new QualificationsDTO();
                    qualificationsDTO.setId(qualifications.getId());
                    qualificationsDTO.setQualificationName(qualifications.getQualificationName());
                    qualificationsDTO.setInstitutionName(qualifications.getInstitutionName());
                    qualificationsDTO.setYearObtained(qualifications.getYearObtained());
                    qualificationsDTO.setProfileId(profile.getId());
                    return qualificationsDTO;
                })
                .collect(Collectors.toList());

        profileDTO.setQualifications(qualificationsDTOList);

        profileDTO.setWorkHistory(workHistoryDTOList);

        return profileDTO;

    }


    // Updating Profile
    public Profile convertDTOToEntityForUpdate(ProfileDTO dto, Profile existingProfile){
        existingProfile.setFirstName(dto.getFirstName());
        existingProfile.setLastName(dto.getLastName());
        existingProfile.setProfileDescription(dto.getProfileDescription());
        existingProfile.setPosition(dto.getPosition());
        return existingProfile;

    }


}
