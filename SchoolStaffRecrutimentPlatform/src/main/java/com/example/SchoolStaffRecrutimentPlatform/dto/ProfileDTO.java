package com.example.SchoolStaffRecrutimentPlatform.dto;

// DTO serves to pass the data between the layers of Controller and Service

// DTO follows Prototype Design Pattern

// constructor in here

import java.util.ArrayList;
import java.util.List;

public class ProfileDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String profileDescription;
    private int appUserId;
    private List<QualificationsDTO> qualificationsDTO = new ArrayList<>();
    private List<WorkHistoryDTO> workHistoryDTO = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public List<QualificationsDTO> getQualificationsDTO() {
        return qualificationsDTO;
    }

    public void setQualificationsDTO(List<QualificationsDTO> qualificationsDTO) {
        this.qualificationsDTO = qualificationsDTO;
    }

    public List<WorkHistoryDTO> getWorkHistoryDTO() {
        return workHistoryDTO;
    }

    public void setWorkHistoryDTO(List<WorkHistoryDTO> workHistoryDTO) {
        this.workHistoryDTO = workHistoryDTO;
    }
}
