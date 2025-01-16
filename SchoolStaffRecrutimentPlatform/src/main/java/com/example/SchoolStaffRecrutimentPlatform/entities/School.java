package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "School")
public class School {

    @Id // Primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long schoolId;

    private String schoolName;
    private String location;
    private String additionalInfo;

    // JPA requires a no args constructor
    public School() {};

    // Use of parameterized constructor
    public School(Long schoolId, String schoolName, String location, String additionalInfo) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.location = location;
        this.additionalInfo = additionalInfo;
    }

    // Getters and setters
    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
