package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static com.example.SchoolStaffRecrutimentPlatform.util.JsonUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private AppUserRepository appUserRepository;

    @MockitoBean
    private JwtUtil jwtUtil;


    @Test
    @WithMockUser(username = "johnDoe")
    public void getProfile_AuthenticatedUser_WillReturnProfile() throws Exception {
        // Arrange
        // Create a mock AppUser
        AppUser mockAppUser = new AppUser();
        mockAppUser.setId(1);
        mockAppUser.setUsername("johnDoe");


        ProfileDTO mockProfileDTO = new ProfileDTO();
        mockProfileDTO.setAppUserId(1);
        mockProfileDTO.setFirstName("John");
        mockProfileDTO.setLastName("Doe");
        mockProfileDTO.setPosition("Year 1 Teacher");
        mockProfileDTO.setProfileDescription("I am a year 1 teacher");

        // Mocking the return of AppUser
        when(appUserRepository.findByUsername("johnDoe")).thenReturn(mockAppUser);
        // Mocking the return of Profile via userId
        when(profileService.getProfileById(1)).thenReturn(mockProfileDTO);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/personal")
                        .accept("application/json"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Year 1 Teacher"))
                .andExpect(jsonPath("$.profileDescription").value("I am a year 1 teacher"));
    }


    @Test
    public void createProfile_WithoutAuthenication_WillReturn404() throws Exception {
        // Arrange - Mock ProfileDTO
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAppUserId(1);
        profileDTO.setFirstName("John");
        profileDTO.setLastName("Doe");
        profileDTO.setPosition("Year 1 Teacher");
        profileDTO.setProfileDescription("I am a year 1 teacher");

        when(profileService.createProfile(any(ProfileDTO.class))).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/profile/create")
                        .contentType("application/json")
                        .content(asJsonString(profileDTO))
                        .accept("application/json"))
                .andExpect(status().isForbidden());  // Forbidden 404
    }





}
