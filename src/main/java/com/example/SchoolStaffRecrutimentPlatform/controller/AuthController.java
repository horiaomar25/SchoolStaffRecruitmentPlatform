package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.dto.JwtResponse;
import com.example.SchoolStaffRecrutimentPlatform.dto.LoginRequest;
import com.example.SchoolStaffRecrutimentPlatform.dto.RegisterRequest;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AppUserImpl;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserImpl appUserService;


    // Authenticates a user and returns JWT token if user details match what is in the database
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String token = jwtUtil.generateToken(loginRequest.getUsername());

            ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
                            .maxAge(Duration.ofHours(1))
                                    .httpOnly(true)
                                            . secure(true)
                                                    .path("/")
                                                            .domain("srs-nu.vercel.app")
                                                                    .sameSite("Strict")
                                                                            .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok("Login Successful");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Validate the JWT token. Check for the format and verifies token.
    @PostMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request) {
       String token = null;

       // Try to get token from Authorization header
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        // Token not found in the heaader
        if(token == null) {
            Cookie[] cookies = request.getCookies();

            if(cookies != null) {
                for (Cookie cookie : cookies){
                    if("jwtToken".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

        }

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found");
        }

        if(jwtUtil.validateToken(token)){
            return ResponseEntity.ok(new JwtResponse("Token is valid"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // Allows user to register username and password.
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }

        appUserService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    // Allow user to logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwtToken", null); // clears the token
        cookie.setMaxAge(0); // clears cookie
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successfully");
    }

    // OPTIONS request handling for /validate
    // OPTIONS request handling for /validate
    @RequestMapping(value = "/validate", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleValidateOptions() {
        System.out.println("Options validate triggered");
        // http header object to add cors control
        // response entity to include header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "https://srs-nu.vercel.app"); // Replace with your frontend origin
        headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Max-Age", "3600");
        return ResponseEntity.ok().headers(headers).build();
    }


    // OPTIONS request handling for /login
    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleLoginOptions() {

        System.out.println("Options login triggered");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "https://srs-nu.vercel.app");
        headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Max-Age", "3600");
        return ResponseEntity.ok().headers(headers).build();
    }


}
