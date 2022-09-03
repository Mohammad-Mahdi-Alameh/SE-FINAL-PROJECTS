package com.example.rently.security.services.userService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.rently.models.user.User;


public interface UserService{
    
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUserById(Long userId);
}
