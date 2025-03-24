package com.example.SchoolStaffRecrutimentPlatform.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
// AbstractAuthenicationToken is base class for authenication tokens in Spring Security. Stores authenicated users
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal; // Represent Authenticated User. Is a object that hold user information

    public JwtAuthenticationToken(UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true); // marks user as authenicated
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    } // returns the UserDetail object
}