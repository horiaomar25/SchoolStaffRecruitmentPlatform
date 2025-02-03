package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualificationsDTO {

    private int id;
    private String qualificationName;
    private String institutionName;
    private int yearObtained;
    private int profileId;

    public QualificationsDTO() {}


}
