package com.paymybuddy.api.domain.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.paymybuddy.api.domain.model.Role;
import com.paymybuddy.api.domain.model.UserApp;

@Component
public class UserMapper {
	public UserLoginDTO UserToDTO(UserApp user) {

		String email = user.getEmail();
		String password = user.getPassword();
		List<Role> roles = user.getRoles();
		
		return new UserLoginDTO (email,password,roles);
	}
}
