package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.dto.ProfileDTO;


public interface ProfileService {
    String createProfile(ProfileDTO profileDTO);
    String deleteProfile(int id);
    String updateProfile(ProfileDTO profileDTO);



}
