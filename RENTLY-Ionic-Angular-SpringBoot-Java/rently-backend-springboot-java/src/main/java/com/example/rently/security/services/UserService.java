package com.example.rently.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.rently.models.User;


public interface UserService{
    
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUserById(Long userId);
}
