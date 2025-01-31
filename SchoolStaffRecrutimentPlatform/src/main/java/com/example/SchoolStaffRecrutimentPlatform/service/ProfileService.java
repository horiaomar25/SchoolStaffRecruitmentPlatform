package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;


public interface ProfileService {
    String createProfile(ProfileDTO profileDTO);
    String deleteProfile(int id);
    String updateProfile(ProfileDTO profileDTO);

    String updateQualification(ProfileDTO profileDTO);
    String updateWorkHistory(ProfileDTO profileDTO);
     ProfileDTO getProfile(int appUserId);



}
