package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String assignmentPosition;

    @Column(columnDefinition = "TEXT")
    private String assignmentDescription;


   @Column
    private LocalDate startDate;

   @Column
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // initially null so that when a user accepts the assignment it will be added to their dashboard
    private AppUser user;




}
