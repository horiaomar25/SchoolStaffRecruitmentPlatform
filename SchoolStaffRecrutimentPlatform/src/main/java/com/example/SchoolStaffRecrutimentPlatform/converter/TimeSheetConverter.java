package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDatesDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheetDates;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSheetConverter {

    public TimeSheetDTO convertTimeSheetToTimeSheetDTO(TimeSheet timeSheet) {
        TimeSheetDTO timeSheetDTO = new TimeSheetDTO();
        timeSheetDTO.setId(timeSheet.getId());

        List<TimeSheetDatesDTO> datesList = timeSheet.getTimeSheetDatesList().stream()
                .map( dates -> {
                    TimeSheetDatesDTO dto = new TimeSheetDatesDTO();
                    dto.setId(dates.getId());
                    dto.setDate(dates.getDate());
                    return dto;
                })
                .toList();

        timeSheetDTO.setTimeSheetDTOList(datesList);

        // Convert Assignment to exclude user details
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAssignmentId(timeSheet.getAssignment().getId());
        assignmentDTO.setAssignmentPosition(timeSheet.getAssignment().getAssignmentPosition());

        assignmentDTO.setStartDate(timeSheet.getAssignment().getStartDate());
        assignmentDTO.setEndDate(timeSheet.getAssignment().getEndDate());
        assignmentDTO.setSchoolId(timeSheet.getAssignment().getSchool().getId());
        assignmentDTO.setSchoolName(timeSheet.getAssignment().getSchool().getSchoolName());

       timeSheetDTO.setAssignment(assignmentDTO);

       return timeSheetDTO;
    }
}
