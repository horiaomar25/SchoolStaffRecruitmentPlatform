package com.example.SchoolStaffRecrutimentPlatform.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomUserDetailService implements UserDetailsService {

    // validate user exists
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("John")){ // make a db call here instead with the help of repository
            return new User("John", "secret", new ArrayList<>());
        } else{
            throw new UsernameNotFoundException("User not found");
        }

        return null;
    }

}
