package com.paymybuddy.api.domain.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String firstName;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(String email, String firstName, String lastName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "email:" + email + ", firstName:" + firstName + ", lastName:" + lastName + '}';
	}
}
