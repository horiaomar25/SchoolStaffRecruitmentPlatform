package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;


public interface ProfileService {
    String createProfile(ProfileDTO profileDTO);

    ProfileDTO getProfileById(int appUserId);

    String deleteProfile(int id);
    String updateProfile(ProfileDTO profileDTO);

    String updateQualification(ProfileDTO profileDTO);
    String updateWorkHistory(ProfileDTO profileDTO);




}
