package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.entities.Availability;
import com.example.SchoolStaffRecrutimentPlatform.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @GetMapping("/getdates")
    public List<Availability> getAllAvailability() {
        return availabilityRepository.findAll();
    }

    @PostMapping("/create")
    public Availability createAvailability(@RequestBody Availability availability) {
        Availability savedAvailability = availabilityRepository.save(availability);
        return availabilityRepository.save(availability);
    }

}
