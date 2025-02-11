package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;


public interface ProfileService {
    ProfileDTO createProfile(ProfileDTO profileDTO) throws UserNotFoundException;

    ProfileDTO getProfileById(int appUserId);

    String deleteProfile(int id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);






}
