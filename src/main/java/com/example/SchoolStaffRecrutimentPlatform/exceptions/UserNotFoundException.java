package com.example.SchoolStaffRecrutimentPlatform.exceptions;

// checked exception as user/authentication is needed to use any data in the api
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
