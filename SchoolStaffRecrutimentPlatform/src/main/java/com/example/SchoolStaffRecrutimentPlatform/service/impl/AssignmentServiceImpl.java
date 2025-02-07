package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl {

    // Inject repositories for User, School, Timesheet and Assignment
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    TimeSheetRepository timeSheetRepository;

    @Autowired
    AppUserRepository appUserRepository;

    // Write method that will generate a timesheet for accepted assignment


}
