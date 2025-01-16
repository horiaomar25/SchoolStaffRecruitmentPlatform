package com.example.SchoolStaffRecrutimentPlatform.dto;

public class QualificationsDTO {
    // Using DTO to help pass the data more smoothly
    private String qualificationName;
    private String result;
    private Long users_id;

    // Getters and Setters
    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String results) {
        this.result = results;
    }

    public Long getUsersId() {
        return users_id;
    }


}
