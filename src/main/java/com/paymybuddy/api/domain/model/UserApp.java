package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public  class UserApp {
	private final String REGEX_P = "^(.+)@(\\S+)$";
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Pattern( regexp = REGEX_P)
	@Email
	@Column(name="email", unique=true)
	private String email;
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="contacts")
	private List<UserApp> contacts;

}
