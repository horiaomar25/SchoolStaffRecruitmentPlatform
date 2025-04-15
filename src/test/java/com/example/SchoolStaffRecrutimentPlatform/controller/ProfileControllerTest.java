package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.ProfileService;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static com.example.SchoolStaffRecrutimentPlatform.util.JsonUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Web Layer Test
@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false) // removes security filters
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
    public void createProfile_WithValidData_WillReturnCreatedStatus() throws Exception{
        // Arrange - Mock ProfileDTO with valid data
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAppUserId(1);
        profileDTO.setFirstName("John");
        profileDTO.setLastName("Doe");
        profileDTO.setPosition("Year 1 Teacher");
        profileDTO.setProfileDescription("I am a year 1 teacher");

        // Mock service
        when(profileService.createProfile(any(ProfileDTO.class))).thenReturn(profileDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/profile/create")
                        .contentType("application/json")
                        .content(asJsonString(profileDTO))
                        .accept("application/json"))
                .andExpect(status().isCreated())  // Expect HTTP 201 Created
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Year 1 Teacher"));
    }


    @Test
    public void getProfile_WithValidJWTToken_WillReturnProfile() throws Exception {
        // Arrange
        // Mock token
        String userName = "johnDoe";
        String token = "valid.jwt.token";

        // Mock app user
        AppUser mockAppUser = new AppUser();
        mockAppUser.setId(1);
        mockAppUser.setUsername(userName);


        ProfileDTO mockProfileDTO = new ProfileDTO();
        mockProfileDTO.setAppUserId(1);
        mockProfileDTO.setFirstName("John");
        mockProfileDTO.setLastName("Doe");
        mockProfileDTO.setPosition("Year 1 Teacher");
        mockProfileDTO.setProfileDescription("I am a year 1 teacher");

       // Mock JWTUtil
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn(userName);

        when(appUserRepository.findByUsername(userName)).thenReturn(mockAppUser);
        when(profileService.getProfileById(1)).thenReturn(mockProfileDTO);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/personal")
                        .header("Authorization", "Bearer " + token)
                        .accept("application/json"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Year 1 Teacher"))
                .andExpect(jsonPath("$.profileDescription").value("I am a year 1 teacher"));
    }

    @Test
    public void updateProfile_WithValidJWTTokenAndProfileData_ReturnUpdatedProfile() throws Exception{
        // Arrange: Set up mock data and behaviors
        String token = "valid.jwt.token";
        String username = "johnDoe";

        // Mock the ProfileDTO with the updated data
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAppUserId(1);
        profileDTO.setFirstName("John");
        profileDTO.setLastName("Doe");
        profileDTO.setPosition("Year 1 Teacher");
        profileDTO.setProfileDescription("I am a year 1 teacher");

        // Mock the updated ProfileDTO (after the update)
        ProfileDTO updatedProfileDTO = new ProfileDTO();
        updatedProfileDTO.setAppUserId(1);
        updatedProfileDTO.setFirstName("John");
        updatedProfileDTO.setLastName("Doe");
        updatedProfileDTO.setPosition("Senior Year 1 Teacher");
        updatedProfileDTO.setProfileDescription("I am a senior year 1 teacher");

        // Mock the AppUser object
        AppUser mockAppUser = new AppUser();
        mockAppUser.setUsername(username);
        mockAppUser.setId(1);

        // Mock the JwtUtil methods
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn(username);

        // Mock the repository to return the mock user
        when(appUserRepository.findByUsername(username)).thenReturn(mockAppUser);

        // Mock the ProfileService to return the updated profile
        when(profileService.updateProfile(any(ProfileDTO.class))).thenReturn(updatedProfileDTO);

        // Act: Perform the PATCH request
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/profile/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(asJsonString(profileDTO)) // Convert ProfileDTO to JSON
                        .accept("application/json"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Senior Year 1 Teacher"))
                .andExpect(jsonPath("$.profileDescription").value("I am a senior year 1 teacher"));
    }



}
