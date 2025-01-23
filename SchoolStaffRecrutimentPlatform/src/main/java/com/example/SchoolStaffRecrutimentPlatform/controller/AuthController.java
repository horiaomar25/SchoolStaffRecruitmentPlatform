package com.example.SchoolStaffRecrutimentPlatform.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    // Post Request to create a login
    @PostMapping("/auth")
    public String auth() {
        return "Hello World";
    }
}
