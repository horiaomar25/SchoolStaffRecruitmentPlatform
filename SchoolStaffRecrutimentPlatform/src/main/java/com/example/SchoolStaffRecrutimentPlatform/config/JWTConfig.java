package com.example.SchoolStaffRecrutimentPlatform.config;

import com.example.SchoolStaffRecrutimentPlatform.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class JWTConfig  {
    @Autowired
   private CustomUserDetailService customUserDetailService;

    // Check authenication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(customUserDetailService);
    }

    // Gives permission on what can be accessed with auth
    @Bean
    public HttpSecurity filterChain(HttpSecurity http) throws Exception {
    return http



    }
}
