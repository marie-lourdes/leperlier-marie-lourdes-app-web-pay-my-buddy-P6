package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "user_app")
public class UserApp {
	private final String REGEX_P = "^(.+)@(\\S+)[.](\\S+)$";

	@Id
	@Pattern(regexp = REGEX_P)
	@NotNull
	@Email
	@Column(name = "email", unique = true)
	private String email;

	@NotNull
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Contact> contacts;

	@NotNull
	@Column(name = "role")
	private String role;

	public List<Contact> addContact(Contact contact) {
		this.contacts.add(contact);
		return this.getContacts();
	}

	@Override
	public String toString() {
		return "UserApp{" + "email:" + email + ", first name:'" + firstName + '\'' + ", last name:" + lastName
				+ ", password:" + password + ", contacts:" + contacts + ", role:" + role + '}';
	}
}
