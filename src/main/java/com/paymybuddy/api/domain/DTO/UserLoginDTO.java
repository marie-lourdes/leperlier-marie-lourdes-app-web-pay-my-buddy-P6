package com.paymybuddy.api.domain.DTO;

import java.util.List;

import com.paymybuddy.api.domain.model.Role;

import lombok.Data;

@Data
public class UserLoginDTO {

	private String email;
	private String password;
	private List<Role> roles;

	public UserLoginDTO() {
	}

	public UserLoginDTO(String email, String password, List<Role> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;

	}
}
