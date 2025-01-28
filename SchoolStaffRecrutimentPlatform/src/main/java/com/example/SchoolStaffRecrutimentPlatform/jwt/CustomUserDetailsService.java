package com.example.SchoolStaffRecrutimentPlatform.jwt;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }
}
