package com.example.SchoolStaffRecrutimentPlatform.dto;

public class AssignmentDTO {
    // Needed for data security as the assignment will contain details about the school.
    // DTO is a good way to transfer data securely
    private Long usersId;
    private Long schoolId;
    private String role;
    private Integer duration;


    // Getters and Setters
    public Long getUsersId() {
        return usersId;
    }


    public Long getSchoolId() {
        return schoolId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getDuration() {
        return duration;
    }




}
