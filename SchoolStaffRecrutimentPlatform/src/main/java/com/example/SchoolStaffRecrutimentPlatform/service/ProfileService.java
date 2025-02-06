package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;


public interface ProfileService {
    ProfileDTO createProfile(ProfileDTO profileDTO);

    ProfileDTO getProfileById(int appUserId);

    String deleteProfile(int id);
    ProfileDTO updateProfile(ProfileDTO profileDTO);






}
