package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "TimeSheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;


    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;


    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private AppUser user;

   // One timesheet per assignment
    @OneToOne
    @JoinColumn(name = "assignment_id", nullable = false, unique = true)
    private Assignment assignment;


}

