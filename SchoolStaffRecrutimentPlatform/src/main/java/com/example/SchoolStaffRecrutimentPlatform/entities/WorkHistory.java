package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table
public class WorkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String schoolName;
    private String role;
    private String duration;

    // Need add School as foreign key - need to create School Entity

}
