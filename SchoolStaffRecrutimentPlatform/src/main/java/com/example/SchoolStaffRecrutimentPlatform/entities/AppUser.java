package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;



// Annotations from JPA
@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // can also use AUTO for increment
    private int id;

    // can specify the varchar using length. If not specified default is varchar(255)
    @Column(nullable = false) // setting to NOT NULL in schema
    private String email;   // mapping name of column to the name of the variable

    @Column(nullable = false)
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
