package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Column(nullable = false)
    private String schoolPicture;

    @Column(nullable = false)
    private String schoolWebsite;


}
