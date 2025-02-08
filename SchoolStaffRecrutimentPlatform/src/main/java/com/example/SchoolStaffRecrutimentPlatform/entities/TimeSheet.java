package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "TimeSheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    // Use the TimeSheetDates to create a list of dates from the assignment to allow user to tick of the days to be submitted

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

