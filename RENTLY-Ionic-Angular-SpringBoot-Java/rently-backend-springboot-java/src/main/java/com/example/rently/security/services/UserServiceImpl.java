package com.example.rently.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rently.exceptions.ResourceNotFoundException;
import com.example.rently.models.User;
import com.example.rently.repository.UserRepository;

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