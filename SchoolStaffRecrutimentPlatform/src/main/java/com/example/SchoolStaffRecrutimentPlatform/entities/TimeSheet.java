package com.example.SchoolStaffRecrutimentPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;


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
    @OneToMany(mappedBy = "timeSheet", cascade = CascadeType.ALL)
    ArrayList<TimeSheetDates> timeSheetDatesList = new ArrayList<>();

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



    // create method that loops through the startDate and endDate to add the date in between to be added to timesheet
    public void createAllDates(){
        LocalDate currentDate = this.startDate;

        while(!currentDate.isAfter(this.endDate)){
            // using constuctor in TimeSheetDates and points to current Timesheet
            TimeSheetDates timeSheetDates = new TimeSheetDates(currentDate,this);

            this.timeSheetDatesList.add(timeSheetDates);

            currentDate = currentDate.plusDays(1); // incrementing the date by one day to add it to the list in the next iteration of the loop

        }

    }


}

