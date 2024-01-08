package com.paymybuddy.api.domain.DTO;

import lombok.Data;

@Data
public class UserLoginDTO {

	private String email;
	private String password;
	private String role;

	public UserLoginDTO() {
	}

	public UserLoginDTO(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserLoginDTO{" + "email:" + email+  ", password:"
				+ password + ", role:" + role + '}';
	}
}
