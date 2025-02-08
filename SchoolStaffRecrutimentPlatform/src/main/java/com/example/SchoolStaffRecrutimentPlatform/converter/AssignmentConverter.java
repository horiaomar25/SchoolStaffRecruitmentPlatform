package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import jakarta.persistence.Column;
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
        assignmentDTO.setSchoolId(assignmentDTO.getSchoolId());
        assignmentDTO.setSchool(assignmentDTO.getSchool());
        assignmentDTO.setAppUserId(assignment.getUser().getId());

        return assignmentDTO;

    }

    public Assignment convertDtoToEntity(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();
        assignment.setId(assignmentDTO.getAssignmentId());
        assignment.setAssignmentPosition(assignmentDTO.getAssignmentPosition());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setStartDate(assignmentDTO.getStartDate());
        assignment.setEndDate(assignmentDTO.getEndDate());
        assignmentDTO.setSchoolId(assignmentDTO.getSchoolId());
        assignmentDTO.setSchool(assignmentDTO.getSchool());
        assignmentDTO.setAppUserId(assignment.getUser().getId());
        return assignment;
    }


}
