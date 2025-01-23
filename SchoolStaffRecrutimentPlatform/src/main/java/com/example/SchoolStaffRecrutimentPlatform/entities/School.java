package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "School")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String schoolName;
    @Column(nullable = false)
    private String schoolAddress;


}
