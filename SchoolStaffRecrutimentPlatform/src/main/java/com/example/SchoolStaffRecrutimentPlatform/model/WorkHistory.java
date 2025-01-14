package com.example.SchoolStaffRecrutimentPlatform.model;

import jakarta.persistence.*;

@Entity
public class WorkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workHistoryId;

    @ManyToOne
    @JoinColumn(name= "users_id",  nullable = false)
    private Users user;

    private String role;
    private Integer duration;

    public WorkHistory() {};

    public WorkHistory(Users user, String role, Integer duration) {
        this.user = user;
        this.role = role;
        this.duration = duration;
    }

    // Getter and Setters
    public long getWorkHistoryId() {
        return workHistoryId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
