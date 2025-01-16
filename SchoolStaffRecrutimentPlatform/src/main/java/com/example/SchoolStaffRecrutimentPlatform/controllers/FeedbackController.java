package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.model.Feedback;
import com.example.SchoolStaffRecrutimentPlatform.repository.FeedbackRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;
import com.example.SchoolStaffRecrutimentPlatform.model.Users;
import com.example.SchoolStaffRecrutimentPlatform.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Feedback feedback = feedbackRepository.findById(id).get();
        if (feedback == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feedback);
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Users user = usersRepository.findById(feedback.getUser().getUsersId()).get();
        School school = schoolRepository.findById(feedback.getSchool().getSchoolId()).get();

        if (user == null || school == null) {
            return ResponseEntity.badRequest().build();
        }

        feedback.setUser(user);
        feedback.setSchool(school);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return ResponseEntity.ok(savedFeedback);
    }


}
