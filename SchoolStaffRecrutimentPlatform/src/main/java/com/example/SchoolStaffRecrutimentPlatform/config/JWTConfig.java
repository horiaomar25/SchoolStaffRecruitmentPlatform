package com.example.SchoolStaffRecrutimentPlatform.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/* TODO:
    1. Spring Security Config - This file - Deals with session management/permission
    2. UserDetailService is interface used to retrieve username and password/comes with methods
    3. Need to create controller that maps request to login
    4. Password Encoder to hide password in the database
    5. Utility class - creates tokens
    6. Filter class - extends OncePerRequestFilter - check JWT on request to the server

 */

@Configuration
@EnableWebSecurity
public class JWTConfig  {



    // tells us what can be accessed by anyone and what needs to be authorized(user must login)
    // Handles session management - to be stateless
    // enable CSRF(Cross Site Request Forgery) tokens as it adds extra layer of security as this is a web application
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//
//
//
//    }
}
