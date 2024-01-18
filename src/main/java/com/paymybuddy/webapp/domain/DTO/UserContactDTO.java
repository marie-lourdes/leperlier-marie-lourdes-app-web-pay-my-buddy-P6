package com.paymybuddy.webapp.domain.DTO;

import lombok.Data;

@Data
public class UserContactDTO {
	private String email;

	public UserContactDTO () {
		
	}
	
	public UserContactDTO (String email) {
		this.email = email;
		
	}

	@Override
	public String toString() {
		return "UserDTO{" + "email:" + email + '}';
	}
}