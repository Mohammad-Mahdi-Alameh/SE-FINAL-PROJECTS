package com.example.rently.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.example.rently.models.User;
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private int phoneNumber;
	private String picture;
	private int isAdmin;

	public UserDetailsImpl(Long id, String firstname , String lastname , String username, String email, String password,int phoneNumber , String picture , int isAdmin) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber ; 
		this.picture = picture;	
		this.isAdmin = isAdmin;	
	}
	public static UserDetailsImpl build(User user) {
//		List<GrantedAuthority> authorities = user.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
//				.collect(Collectors.toList());
		return new UserDetailsImpl(
			    user.getId(), 
			    user.getFirstname(),
			    user.getLastname(),
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(),
				user.getPhoneNumber(),
				user.getPicture(),
				user.getIsAdmin());

	}

	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
//	public interface UserDetailsService {
//	    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//	}
}
