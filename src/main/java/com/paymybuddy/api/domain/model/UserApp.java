package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_contact_id")
	private List<UserApp> contacts;

	@ManyToMany
	@JoinTable(name = "user_app_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public UserApp() {
	}

	public UserApp(String email, String password, List<Role> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;

	}
	
	@Override
	public String toString() {
		return "UserApp{" + "email:" + email+ ", first name:'" + firstName + '\'' + ", last name:" + lastName + ", password:"
				+ password + ", contacts:" + contacts+ ", roles:" + roles + '}';
	}
}
