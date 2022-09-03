package com.example.rently.security.services.userService.userServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rently.exceptions.ResourceNotFoundException;
import com.example.rently.models.user.User;
import com.example.rently.repositories.user.UserRepository;
import com.example.rently.security.services.userDetailsImplementation.UserDetailsImpl;
import com.example.rently.security.services.userService.UserService;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {

	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
	
	@Override
	public User getUserById(Long userId){
		return userRepository.findById(userId).orElseThrow(() -> 
						new ResourceNotFoundException("User", "Id", userId));
		
	}
	
}