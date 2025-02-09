package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeSheetDTO {
    private int id;

  private List<TimeSheetDatesDTO> timeSheetDTOList;
    private AssignmentDTO assignment;

    public TimeSheetDTO() {}


}
