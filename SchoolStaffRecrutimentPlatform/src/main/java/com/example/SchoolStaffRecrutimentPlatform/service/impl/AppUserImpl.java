package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Handles user registeration and login. Sending to the Entity class/ Database
@Service
public class AppUserImpl {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser registerUser(String email, String password) {
        AppUser appUser = new AppUser();
        appUser.setUsername(email);
        appUser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appUser);
    }

    public AppUser findByEmail(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Transactional
    public void deleteByEmail(String username) {
        appUserRepository.deleteByUsername(username);
    }
}
