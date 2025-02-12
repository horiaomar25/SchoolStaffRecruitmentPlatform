package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.AppUserDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.SchoolDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import org.springframework.stereotype.Component;

@Component
public class AssignmentConverter {

    public AssignmentDTO convertEntityToDto(Assignment assignment) {
        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setAssignmentId(assignment.getId());
        assignmentDTO.setAssignmentPosition(assignment.getAssignmentPosition());
        assignmentDTO.setAssignmentDescription(assignment.getAssignmentDescription());
        assignmentDTO.setStartDate(assignment.getStartDate());
        assignmentDTO.setEndDate(assignment.getEndDate());

        if(assignment.getUser() != null) {
            AppUserDTO appUserDTO = new AppUserDTO();
            appUserDTO.setId(assignment.getUser().getId());
            appUserDTO.setUsername(assignment.getUser().getUsername());
            assignmentDTO.setAppUserDTO(appUserDTO); // will exclude password from response
        }

        if(assignment.getSchool() != null) {

            SchoolDTO schoolDTO = new SchoolDTO();

            schoolDTO.setId(assignment.getSchool().getId());

            schoolDTO.setSchoolName(assignment.getSchool().getSchoolName());

            schoolDTO.setSchoolAddress(assignment.getSchool().getSchoolAddress());

            schoolDTO.setSchoolPicture(assignment.getSchool().getSchoolPicture());

            schoolDTO.setSchoolWebsite(assignment.getSchool().getSchoolWebsite());

            assignmentDTO.setSchoolDTO(schoolDTO);

        }


        return assignmentDTO;


    }


}
