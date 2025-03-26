package com.example.SchoolStaffRecrutimentPlatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService; // Import UserDetailsService

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class JWTConfig {

    @Autowired
    private UserDetailsService userDetailsService; // Inject UserDetailsService

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll() // Allow login and register
                .requestMatchers("/api/v1/profile/delete/{id}", "/api/v1/profile/personal", "/api/v1/profile/update", "/api/v1/assignments/accepted", "/api/v1/assignments/{assignmentId}/accept", "/api/v1/assignments/{assignmentId}/timesheet", "/api/v1/assignments/{assignmentId}/timesheet").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin(withDefaults()) // Enable form-based login
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Use sessions

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}