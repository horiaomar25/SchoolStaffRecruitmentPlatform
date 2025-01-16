package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.model.WorkHistory;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.WorkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/workhistory")
public class WorkHistoryController {
    private WorkHistoryRepository workHistoryRepository;
    private UserRepository userRepository;

    @Autowired
    public WorkHistoryController(WorkHistoryRepository workHistoryRepository, UserRepository userRepository) {
        this.workHistoryRepository = workHistoryRepository;
        this.userRepository = userRepository;
    }

    // GET request to get all workHistory
    @GetMapping
    public List<WorkHistory> getAllWorkHistory(){
        return workHistoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkHistory> getWorkHistoryById(@PathVariable Long id) {

    }

}
