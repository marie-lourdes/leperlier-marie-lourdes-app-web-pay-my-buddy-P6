package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact{
	private final String REGEX_P = "^(.+)@(\\S+)$";
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id_contact")
	private Integer id;
	
	@NotNull
	@Pattern(regexp = REGEX_P)
	@Column(name = "email_contact")
	private String emailContact;

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserApp user;

	@Override
	public String toString() {
		return "UserApp{" + "email:" + emailContact+ ", first name:'" + firstName + '\'' + ", last name:" + lastName + ", user_" + user+ '}';
	}
}