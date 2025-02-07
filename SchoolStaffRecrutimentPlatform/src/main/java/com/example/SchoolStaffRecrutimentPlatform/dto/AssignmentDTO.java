package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AssignmentDTO {
    private String assignmentId;
    private String assignmentPosition;
    private String assignmentDescription;
    private Date startDate;
    private Date endDate;
    private int schoolId;
    private SchoolDTO school;
    private Integer appUserId;

}
