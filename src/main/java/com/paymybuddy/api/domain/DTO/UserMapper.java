package com.paymybuddy.api.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.api.domain.model.UserApp;

@Component
public class UserMapper {
	public UserLoginDTO UserToUserLoginDTO(UserApp user) {
		String email = user.getEmail();
		String password = user.getPassword();
	    String role = user.getRole();
		
		return new UserLoginDTO (email,password,role);
	}
	
	public UserDTO UserToUserDTO(UserApp user) {
		String email = user.getEmail();
	    String firstName = user.getFirstName();	
	    String lastName = user.getLastName();	
	     
		return new UserDTO (email, firstName,lastName);
	}
}
