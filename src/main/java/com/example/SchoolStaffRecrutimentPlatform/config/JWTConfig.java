package com.example.SchoolStaffRecrutimentPlatform.config;

import com.example.SchoolStaffRecrutimentPlatform.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// Configuration Setting for Spring Security
@Configuration
@EnableWebSecurity
public class JWTConfig {
    // Filter class deals with JWT token validation
    private JwtFilter jwtFilter;

    // setting jwtFilter
    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Configure security settings for application using filter chain goes in order.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/logout").permitAll()// these endpoints allow to be accessed without authentication
                .requestMatchers("/api/v1/profile/delete/{id}","/api/v1/profile/personal",  "/api/v1/profile/update", "/api/v1/assignments/accepted","/api/v1/assignments/{assignmentId}/accept","/api/v1/assignments/{assignmentId}/timesheet","/api/v1/assignments/{assignmentId}/timesheet").authenticated() // cannot delete without authenication
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}