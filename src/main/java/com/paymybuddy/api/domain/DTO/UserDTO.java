package com.paymybuddy.api.domain.DTO;

public class UserDTO {
	private String email;
	private String role;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String email, String role) {
		this.email = email;
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "email:" + email + ", role:" + role + '}';
	}
}
