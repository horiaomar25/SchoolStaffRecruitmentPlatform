package com.example.SchoolStaffRecrutimentPlatform.jwt;

import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            logger.info("JWT Token found in cookie: {}", token);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails, authorities));
                    logger.info("User authenticated: {}", username);
                } catch (Exception e) {
                    logger.error("Error loading user details for: {}", username, e);
                }
            } else {
                logger.warn("Invalid JWT Token: {}", token);
            }
        } else {
            logger.warn("JWT Token not found in cookie.");
        }

        filterChain.doFilter(request, response);
    }
}