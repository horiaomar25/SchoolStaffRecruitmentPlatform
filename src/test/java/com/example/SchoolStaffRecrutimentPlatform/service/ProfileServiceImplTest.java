package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.converter.ProfileConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.QualificationsDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.ProfileNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.ProfileRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Junit5 implementation  - Junit4 uses RunWith
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    // Mock all dependencies in ProfileServiceImpl
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private ProfileConverter profileConverter;

    @InjectMocks
    private ProfileServiceImpl profileServiceImpl; // inject the class to be tested


    // naming convention for test - method name_scenario(condition)_expected results

    @Test
    void createAProfile_whenUserDoesNotExist_thenThrowUserNotFoundException() {
        // Arrange - ProfileDTO passed into method
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName("Sarah");
        profileDTO.setLastName("Brown");
        profileDTO.setProfileDescription("I am a year 2 teacher of 6 years.");
        profileDTO.setPosition("Year 2 Teacher");
        profileDTO.setAppUserId(1);
        profileDTO.setQualifications(List.of(new QualificationsDTO()));
        profileDTO.setWorkHistory(List.of(new WorkHistoryDTO()));


        // Act - returning empty optional
        when(appUserRepository.findById(1)).thenReturn(Optional.empty());

        // Assert
        UserNotFoundException exceptedException = assertThrows(UserNotFoundException.class, () -> profileServiceImpl.createProfile(profileDTO));

        // checks that the exception has the correct message
        assertEquals("User not found", exceptedException.getMessage());

    }

    @Test
    void createAProfile_whenUserExists_addNewProfile() throws UserNotFoundException {
        // Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName("Sarah");
        profileDTO.setLastName("Brown");
        profileDTO.setProfileDescription("I am a year 2 teacher of 6 years.");
        profileDTO.setPosition("Year 2 Teacher");
        profileDTO.setAppUserId(1);
        profileDTO.setQualifications(List.of(new QualificationsDTO()));
        profileDTO.setWorkHistory(List.of(new WorkHistoryDTO()));

        AppUser appUser = new AppUser();
        // Act - returning Optional Object of AppUser
        when(appUserRepository.findById(1)).thenReturn(Optional.of(new AppUser()));

        Profile profileEntity = new Profile();
        when(profileConverter.convertDTOToEntity(profileDTO)).thenReturn(profileEntity);

        when(profileRepository.save(profileEntity)).thenReturn(profileEntity);

        // will convert a entitytoDTO and return a DTO
        when(profileConverter.convertEntityToDTO(profileEntity)).thenReturn(profileDTO);

        // stores the created DTO
        ProfileDTO results = profileServiceImpl.createProfile(profileDTO);

        // Assert - should match mock data
        assertEquals("Sarah", results.getFirstName());

    }

    @Test
    void getProfileById_WhenProfileExists_returnProfileDTO() {
        // Arrange: Create a ProfileDTO that will be returned from convertEntityToDTO
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName("Sarah");
        profileDTO.setLastName("Brown");
        profileDTO.setProfileDescription("I am a year 2 teacher of 6 years.");
        profileDTO.setPosition("Year 2 Teacher");
        profileDTO.setAppUserId(1);
        profileDTO.setQualifications(List.of(new QualificationsDTO()));
        profileDTO.setWorkHistory(List.of(new WorkHistoryDTO()));

        // Create a Profile entity
        Profile profileEntity = new Profile();
        profileEntity.setFirstName("Sarah");
        profileEntity.setLastName("Brown");
        profileEntity.setProfileDescription("I am a year 2 teacher of 6 years.");

        // Mocking behaviour
        when(profileRepository.findById(1)).thenReturn(Optional.of(profileEntity));

        when(profileConverter.convertEntityToDTO(profileEntity)).thenReturn(profileDTO);

        // Act: Call the getProfileById method
        ProfileDTO result = profileServiceImpl.getProfileById(1);

        // Assert: Check if the returned ProfileDTO has the expected first name
        assertEquals("Sarah", result.getFirstName(), "The first name should be Sarah");

    }

   @Test
    void getProfileById_WhenProfileDoesNotExist_ThrowProfileNotFoundException() {
       // Arrange
       int appUserId = 1;

       // Act
       when(profileRepository.findById(appUserId)).thenReturn(Optional.empty());

       ProfileNotFoundException expectedException = assertThrows(ProfileNotFoundException.class, () -> profileServiceImpl.getProfileById(appUserId));
       // Assert
       assertEquals("Profile not found", expectedException.getMessage());

   }

   @Test
    void deleteProfile_WhenProfileDoesNotExist_throwProfileNotFoundException() {
        // Arrange
        int profileId = 1;

        // Act
       when(profileRepository.findById(1)).thenReturn(Optional.empty());

       ProfileNotFoundException expectedException = assertThrows(ProfileNotFoundException.class, () -> profileServiceImpl.deleteProfile(profileId));

       assertEquals("Profile not found", expectedException.getMessage());

   }

   @Test
    void deleteProfile_WhenProfileExists_successfullyDeleted() {
        // Arrange
        int profileId = 1;
        Profile existingProfile= new Profile();
        existingProfile.setId(1);

        // Act
       when(profileRepository.findById(1)).thenReturn(Optional.of(existingProfile));

       //Assert
       String result = profileServiceImpl.deleteProfile(profileId);

       assertEquals("Profile deleted successfully", result);

   }

   @Test
    void updateProfile_WhenProfileExists_updateProfileDTO() {
        int profileId = 1;

        Profile existingprofileEntity = new Profile();
       existingprofileEntity.setFirstName("Sarah");
       existingprofileEntity.setLastName("Brown");
       existingprofileEntity.setProfileDescription("I am a year 2 teacher of 6 years.");

       ProfileDTO updatedProfileDTO = new ProfileDTO();
       updatedProfileDTO.setId(profileId);
       updatedProfileDTO.setFirstName("Jane");
       updatedProfileDTO.setLastName("Brown");
       updatedProfileDTO.setProfileDescription("I am a year 2 teacher of 6 years.");


       when(profileRepository.findById(profileId)).thenReturn(Optional.of(existingprofileEntity));

       when(profileConverter.convertDTOToEntityForUpdate(updatedProfileDTO,existingprofileEntity)).thenReturn(existingprofileEntity);

       when(profileRepository.save(existingprofileEntity)).thenReturn(existingprofileEntity);

       when(profileConverter.convertEntityToDTO(existingprofileEntity)).thenReturn(updatedProfileDTO);

       ProfileDTO result = profileServiceImpl.updateProfile(updatedProfileDTO);

       assertEquals("Jane", result.getFirstName(), "The first name should be Jane");



   }

   @Test
    void updateProfile_WhenProfileDoesNotExist_throwProfileNotFoundException() {
        int profileId = 1;
       ProfileDTO updatedProfileDTO = new ProfileDTO();
       updatedProfileDTO.setId(profileId);
       updatedProfileDTO.setFirstName("Jane");
       updatedProfileDTO.setLastName("Brown");
       updatedProfileDTO.setProfileDescription("I am a year 2 teacher of 6 years.");

       when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

       ProfileNotFoundException expectedException = assertThrows(ProfileNotFoundException.class, () -> profileServiceImpl.updateProfile(updatedProfileDTO));

       assertEquals("Profile not found", expectedException.getMessage());

   }




}
