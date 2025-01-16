package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.entities.Feedback;
import com.example.SchoolStaffRecrutimentPlatform.repository.FeedbackRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private FeedbackRepository feedbackRepository;
    private SchoolRepository schoolRepository;
    private UserRepository usersRepository;

    @Autowired
    public FeedbackController(FeedbackRepository feedbackRepository, SchoolRepository schoolRepository, UserRepository usersRepository) {
        this.feedbackRepository = feedbackRepository;
        this.schoolRepository = schoolRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }






}
