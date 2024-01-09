package com.paymybuddy.api.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "user_app")
public class UserApp {
	private final String REGEX_P = "^(.+)@(\\S+)$";
	
	@Id
	@Pattern(regexp = REGEX_P)
	@Email
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy="user")
	private List<Contact> contacts;
	
	@Column(name = "role")
	private String role;

	public UserApp() {
	}

	public UserApp(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public  List<Contact>addContact(Contact contact) {
		this.contacts.add(contact);
		return this.getContacts();
	}
	
/*public void getContact() {
		for(Contact contact : this.contacts) {
			System.out.println(contact);
		}
	}*/
	@Override
	public String toString() {
		return "UserApp{" + "email:" + email+ ", first name:'" + firstName + '\'' + ", last name:" + lastName + ", password:"
				+ password + ", contacts:" + contacts+ ", role:" + role + '}';
	}
}
