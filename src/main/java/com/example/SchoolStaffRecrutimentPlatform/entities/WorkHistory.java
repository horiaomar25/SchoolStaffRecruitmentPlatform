package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@Entity
@Table
public class WorkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String schoolName;
    private String role;
    private String duration;


    @OneToOne
    @JoinColumn(name="school_id")
    private School school;

    // FK for profile
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

  public WorkHistory() {}


}
