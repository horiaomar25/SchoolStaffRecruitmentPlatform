package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Qualifications")
public class Qualifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String qualificationName;
    private String institutionName;
    private int yearObtained;


    // Foriegn Key for Profile
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
