package com.example.SchoolStaffRecrutimentPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailabilityDTO {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private AppUserDTO appUserDTO;



}
