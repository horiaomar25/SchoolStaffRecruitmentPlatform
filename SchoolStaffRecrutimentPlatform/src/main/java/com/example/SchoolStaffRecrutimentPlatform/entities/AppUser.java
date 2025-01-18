package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;



// Annotations from JPA
@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // can also use AUTO for increment
    private int id;

    @Column(nullable = false) // setting to NOT NULL in schema
    private String email;   // mapping name of column to the name of the variable

    @Column(nullable = false)
    private String password;


}
