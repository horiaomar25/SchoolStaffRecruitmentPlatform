package com.example.SchoolStaffRecrutimentPlatform.dto;

// DTO for token that encapsulate the JWT that will be returned to the client upon successful login.
// User logins and server create token JWTResponse obj created and returned to client.
public class JwtResponse {
    // Holds token in a string
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Allow other parts of the application to access token
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
