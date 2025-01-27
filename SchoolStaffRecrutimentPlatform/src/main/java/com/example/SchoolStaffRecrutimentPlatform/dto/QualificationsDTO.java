package com.example.SchoolStaffRecrutimentPlatform.dto;

public class QualificationsDTO {

    private int id;
    private String qualificationName;
    private String institutionName;
    private int yearObtained;
    private int profileId;

    public QualificationsDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public int getYearObtained() {
        return yearObtained;
    }

    public void setYearObtained(int yearObtained) {
        this.yearObtained = yearObtained;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
