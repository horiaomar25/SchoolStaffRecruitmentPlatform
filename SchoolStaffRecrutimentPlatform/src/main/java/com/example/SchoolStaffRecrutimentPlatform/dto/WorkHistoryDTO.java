package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkHistoryDTO {

    private int id;
    private String schoolName;
    private String role;
    private String duration;
    private int schoolId;
    private int profileId;

    public WorkHistoryDTO() {}


}
