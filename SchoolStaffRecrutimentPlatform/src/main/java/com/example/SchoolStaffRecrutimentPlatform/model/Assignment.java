package com.example.SchoolStaffRecrutimentPlatform.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "Assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignment_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users users;


    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String role;
    private Integer duration;
    private String feedback;

    // Default constructor
    public Assignment() {}

    // Parameterized constructor
    public Assignment(Users users, School school, String role, Integer duration, String feedback) {
        this.users = users;
        this.school = school;
        this.role = role;
        this.duration = duration;
        this.feedback = feedback;
    }

    // Getters and Setters
    public Long getAssignmentId() {
        return assignment_id;
    }

    public void setAssignmentId(Long assignment_id) {
        this.assignment_id = assignment_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}