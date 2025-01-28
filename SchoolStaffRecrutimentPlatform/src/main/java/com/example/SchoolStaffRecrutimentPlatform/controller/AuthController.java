package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.JwtResponse;
import com.example.SchoolStaffRecrutimentPlatform.dto.LoginRequest;
import com.example.SchoolStaffRecrutimentPlatform.dto.RegisterRequest;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AppUserImpl;
import com.example.SchoolStaffRecrutimentPlatform.jwt.JwtUtil;
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


    // Using generics on login because of various response in data types that may occur. Keeping it open atm.
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserService.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }

        appUserService.registerUser(registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

   @DeleteMapping("/delete")
   public ResponseEntity<String> deleteUser(@RequestParam String email) {
        appUserService.deleteByEmail(email);
        return ResponseEntity.ok("User deleted successfully");
   }

}
