package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
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
