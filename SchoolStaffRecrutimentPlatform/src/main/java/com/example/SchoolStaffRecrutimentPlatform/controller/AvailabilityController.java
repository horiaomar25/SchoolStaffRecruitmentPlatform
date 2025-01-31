package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Availability;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/getdates")
    public List<Availability> getAllAvailability() {
        return availabilityRepository.findAll();
    }

    @PostMapping("/create")
    public Availability createAvailability(@RequestBody Availability availability) {
        Optional<AppUser> user = appUserRepository.findById(availability.getUser().getId());

        Availability savedAvailability = new Availability();
        savedAvailability.setStartDate(availability.getStartDate());
        savedAvailability.setEndDate(availability.getEndDate());
        savedAvailability.setUser(user.get());

        return availabilityRepository.save(savedAvailability);
    }

}
