package com.example.SchoolStaffRecrutimentPlatform.controllers;

import com.example.SchoolStaffRecrutimentPlatform.entities.Users;
import com.example.SchoolStaffRecrutimentPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
     // calling in repository
    private final UserRepository userRepository;

    // inject into User Controller
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
            return ResponseEntity.notFound().build(); // http status 404 Not Found returned
        }
        return ResponseEntity.ok(user); // return http status 200 ok response
    }

    // POST request to create a new user
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return userRepository.save(user); // update the data
    }


}
