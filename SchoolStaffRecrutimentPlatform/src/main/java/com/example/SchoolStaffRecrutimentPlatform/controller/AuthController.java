package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.JwtResponse;
import com.example.SchoolStaffRecrutimentPlatform.dto.LoginRequest;
import com.example.SchoolStaffRecrutimentPlatform.dto.RegisterRequest;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AppUserImpl;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserImpl appUserService;


    // Authenticates a user and returns JWT token if user details match what is in the database
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Validate the JWT token. Check for the format and verifies token.
    @PostMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String token = authHeader.substring(7);

        if(jwtUtil.validateToken(token)) {
            return ResponseEntity.ok(new JwtResponse("Token is valid"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // Allows user to register username and password.
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }

        appUserService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }




}
