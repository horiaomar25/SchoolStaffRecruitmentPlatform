package com.example.SchoolStaffRecrutimentPlatform.jwt;

import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

// Designed to intercept http requests and set up Security context. Allows your application to perform stateless authentication and authorization.
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        // **COMMENTED OUT: Cookie-based token extraction**
        // Cookie[] cookies = request.getCookies();
        //
        // if(cookies != null){
        //     for(Cookie cookie : cookies){
        //         if("jwtToken".equals(cookie.getName())){
        //             token = cookie.getValue();
        //             break;
        //         }
        //     }
        // }
        //
        // if(token != null && jwtUtil.validateToken(token)){
        //     String username = jwtUtil.getUsernameFromToken(token);
        //
        //     UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //
        //     Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        //
        //     SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails, authorities));
        // }

        // **UNCOMMENTED and ACTIVE: Authorization header-based token extraction and validation**
        String header = request.getHeader("Authorization");
        // checks if header starts with bearer
        if (header != null && header.startsWith("Bearer ")) {
            // extracts token after the word "BEARER " with space
            token = header.substring(7);
            // use jwtUtil class to validate token
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails, authorities));
            }
        }

        // continues with rest of the filter chain
        filterChain.doFilter(request, response);
    }
}