package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;

/* TODO:
*   Find how to implement Text in Hibernate
*   */

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
    @Column()
    private String profileDescription;


    @OneToOne
    @JoinColumn(name="users_id")
    private AppUser appUser;


}
