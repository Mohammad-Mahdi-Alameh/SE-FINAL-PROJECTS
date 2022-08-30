package com.example.rently.models;
import java.time.LocalDateTime;

//import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@CreationTimestamp
    private LocalDateTime createDateTime;
 
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

	@NotEmpty
	@Size(max = 20)
    @Column(name = "firstname" )
	private String firstname;

	@NotEmpty
	@Size(max = 20)
    @Column(name = "lastname" )
	private String lastname;

	@NotEmpty
	@Size(max = 20)
    @Column(name = "username" )
	private String username;

	@NotEmpty
	@Size(max = 50)
	@Email
    @Column(name = "email" )
	private String email;

	@NotEmpty
	@Size(max = 120)
    @Column(name = "password" )
	private String password;

	@NotNull
    @Column(name = "phonenumber" )
	private int phoneNumber;
	
	@NotEmpty
	@Lob
    @Column(name = "picture" )
	private String picture;

	@NotNull
	@Column(name = "is_admin" )
	private int isAdmin;
	
	public User() {
	}

	public User(String firstName , String lastName,String username, String email, String password , int phoneNumber , String picture , int isAdmin) {
		this.firstname = firstName;
		this.lastname = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.picture = picture;
		this.isAdmin = isAdmin;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
