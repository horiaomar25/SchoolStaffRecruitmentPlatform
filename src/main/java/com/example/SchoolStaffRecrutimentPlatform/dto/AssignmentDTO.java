package com.example.SchoolStaffRecrutimentPlatform.dto;

import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
// exclude null values from JSON response as the use of Assignment DTO differs.
public class AssignmentDTO {
    private int assignmentId;
    private String assignmentPosition;
    private String assignmentDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private String schoolName;
    private int schoolId;
    private SchoolDTO schoolDTO;
    private AppUserDTO appUserDTO;



}
