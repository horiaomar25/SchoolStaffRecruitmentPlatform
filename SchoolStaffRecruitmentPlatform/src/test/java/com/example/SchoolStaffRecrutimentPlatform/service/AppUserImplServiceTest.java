package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AppUserImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserImplServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    AppUserImpl appUserImpl;

    @Test
    void registerUser_createUsernameAndPassword_saveWithEncodedPassword() {
        // Arrange - Mock user
        AppUser appUser = new AppUser();
        String userName = "JohnDoe";
        String password = "password";
        String encodedPassword = "encodedPassword";
        appUser.setUsername(userName);


        // behaviour of password encoder
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Save to db any instance of the AppUser class/entity
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);
        // Act
        AppUser result = appUserImpl.registerUser(userName, password);
        // Assert
        assertEquals(userName, result.getUsername());
    }

    @Test
    void findByUsername_WhenUserExists(){
        AppUser appUser = new AppUser();

        String userName = "JohnDoe";

        appUser.setUsername(userName);

        when(appUserRepository.findByUsername(userName)).thenReturn(appUser);

        AppUser result = appUserImpl.findByUsername(userName);

        assertEquals(userName, result.getUsername());
    }






}
