package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// Annotations from JPA
@Getter
@Setter
@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // can also use AUTO for increment
    private int id;

    // can specify the varchar using length. If not specified default is varchar(255)
    @Column(nullable = false) // setting to NOT NULL in schema
    private String username;   // mapping name of column to the name of the variable

    @Column(nullable = false)
    private String password;



}
