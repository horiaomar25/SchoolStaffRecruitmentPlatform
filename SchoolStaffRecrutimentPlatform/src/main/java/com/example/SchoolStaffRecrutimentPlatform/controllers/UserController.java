package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.model.Users;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET request to get all users
    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // GET request to get a specific user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = userRepository.findById(id).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // POST request to create a new user
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return userRepository.save(user);
    }

    // DELETE request to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Users user = userRepository.findById(id).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
