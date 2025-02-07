package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolDTO {

    private int id;
    private String schoolName;
    private String schoolAddress;
    private String schoolPicture;
    public SchoolDTO() {}

}
