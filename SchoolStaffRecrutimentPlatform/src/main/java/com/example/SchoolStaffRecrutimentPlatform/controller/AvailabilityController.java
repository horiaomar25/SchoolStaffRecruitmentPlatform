package com.example.SchoolStaffRecrutimentPlatform.controller;


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




}
