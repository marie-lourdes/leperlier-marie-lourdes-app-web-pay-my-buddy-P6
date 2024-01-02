package com.paymybuddy.api.domain.DTO;

import java.util.List;

import com.paymybuddy.api.domain.model.Role;

@Data
public class UserLoginDTO {
	private String email;
	private String password;
	private List<Role> roles;
}
