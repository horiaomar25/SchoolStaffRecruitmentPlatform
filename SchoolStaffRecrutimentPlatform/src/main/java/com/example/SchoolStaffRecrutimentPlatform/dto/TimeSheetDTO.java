package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TimeSheetDTO {
    private int id;
    private LocalDate startTime;
    private LocalDate endTime;
    private SchoolDTO school;
    private AppUserDTO user;
    private AssignmentDTO assignment;
    private int assignmentId;
    public TimeSheetDTO() {}

}
