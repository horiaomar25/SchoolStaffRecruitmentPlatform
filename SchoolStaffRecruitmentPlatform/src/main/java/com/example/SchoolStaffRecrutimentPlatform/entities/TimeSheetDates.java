package com.example.SchoolStaffRecrutimentPlatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table
public class TimeSheetDates {

    // List the dates between the startDate and endDate from the Assignment Entity to create the timesheet
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date; // holding each date for the timesheet for user to tick off

    @ManyToOne
    @JoinColumn(name = "timesheet_id", nullable = false)// foriegn key to TimeSheet
    @JsonIgnore  // used to prevent infinite data from the list
    private TimeSheet timeSheet;

    public TimeSheetDates() {}

    public TimeSheetDates(LocalDate date, TimeSheet timeSheet) {
        this.date = date;
        this.timeSheet = timeSheet;
    }
}
