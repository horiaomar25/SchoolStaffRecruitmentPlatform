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

    private LocalDate startDate;
    private LocalDate endDate;
    private int schoolId;
    private String schoolName;



}
