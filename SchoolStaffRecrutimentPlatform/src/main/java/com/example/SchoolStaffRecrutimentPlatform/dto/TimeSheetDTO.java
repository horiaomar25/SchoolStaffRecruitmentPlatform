package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeSheetDTO {
    private int id;

    private List<TimeSheetDatesDTO> timeSheetDates;
    private AssignmentDTO assignment;
    private AppUserDTO appUser;

    public TimeSheetDTO() {}


}
