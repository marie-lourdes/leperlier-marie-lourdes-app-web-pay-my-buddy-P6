package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact{
	private final String REGEX_P = "^(.+)@(\\S+)$";
	@Id
	@Pattern(regexp = REGEX_P)
	@Email
	@Column(name = "id_contact", unique = true)
	private String idContact;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name="user_id")
	private UserApp user;


/*	public UserApp() {
	}

	public UserApp(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;

	}*/
	
	/*@Override
	public String toString() {
		return "UserApp{" + "email:" + idContact+ ", first name:'" + firstName + '\'' + ", last name:" + lastName + ", user" + user+ '}';
	}*/
}
