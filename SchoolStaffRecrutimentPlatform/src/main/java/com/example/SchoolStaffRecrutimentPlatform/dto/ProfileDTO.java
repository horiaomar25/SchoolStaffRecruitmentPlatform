package com.example.SchoolStaffRecrutimentPlatform.dto;

// DTO serves to pass the data between the layers of Controller and Service

// DTO follows Prototype Design Pattern

// constructor in here

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProfileDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String profileDescription;
    private int appUserId;
    private List<QualificationsDTO> qualificationsDTO = new ArrayList<>();
    private List<WorkHistoryDTO> workHistoryDTO = new ArrayList<>();


}
