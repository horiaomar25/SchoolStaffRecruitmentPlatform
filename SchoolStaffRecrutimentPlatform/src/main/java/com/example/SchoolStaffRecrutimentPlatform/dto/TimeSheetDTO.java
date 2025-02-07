package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TimeSheetDTO {
    private int id;
    private Date startTime;
    private Date endTime;
    private SchoolDTO school;
    private AppUserDTO user;
    private AssignmentDTO assignment;
    public TimeSheetDTO() {}

}
