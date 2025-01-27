package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // setting to NOT NULL , default varchar(255) without declaring length
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String position;

    // need TEXT to be added
    @Column(columnDefinition = "TEXT")
    private String profileDescription;

   // One user is associated to one Profile
    @OneToOne
    @JoinColumn(name="users_id")
    private AppUser appUser;

    // A profile can multiple entries of Work History - WorkHistory in List
    // CASADE defines the behaviour of a parent entity and its related entities.
    // PROFILE - parent entity
    // OneToMany one Profile -> Qualifications
    // Bidirectional Relationship as both entities have reference to the other.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    List<WorkHistory> workHistories = new ArrayList<>();


    // A profile can have multiple entries of Qualification
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
   List<Qualifications> qualifications = new ArrayList<>();

    public Profile() {}

}
