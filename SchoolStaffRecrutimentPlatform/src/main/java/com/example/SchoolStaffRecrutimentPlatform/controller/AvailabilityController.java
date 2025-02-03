package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.AppUserDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.AvailabilityDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Availability;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/availability")
@CrossOrigin(origins = "http://localhost:3000")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/getdates")
    public List<AvailabilityDTO> getAllAvailability() {
        List<Availability> availabilities = availabilityRepository.findAll();

        // Convert the list of Availability to AvailabilityDTO
        List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();
        for (Availability availability : availabilities) {
            AppUserDTO userDTO = new AppUserDTO(availability.getUser().getId(), availability.getUser().getUsername());
            AvailabilityDTO availabilityDTO = new AvailabilityDTO(availability.getId(), availability.getStartDate(), availability.getEndDate(), userDTO);
            availabilityDTOs.add(availabilityDTO);
        }

        return availabilityDTOs;
    }

    @PostMapping("/create")
    public Availability createAvailability(@RequestBody AvailabilityDTO availabilityDTO, Principal principal) {
        // Get authenticated user
        String username = principal.getName();
        AppUser user = appUserRepository.findByUsername(username);

        // Create the Availability entity from the DTO
        Availability availability = new Availability();
        availability.setStartDate(availabilityDTO.getStartDate());
        availability.setEndDate(availabilityDTO.getEndDate());
        availability.setUser(user);

        // Save to the repository
        return availabilityRepository.save(availability);
    }


}
