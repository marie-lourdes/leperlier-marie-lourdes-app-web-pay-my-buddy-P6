package com.paymybuddy.api.domain.DTO;

import java.util.List;

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
	    String role = user.getRole();	
	    
		return new UserDTO (email,role);
	}

	/*public UserContactDTO UserToUserContactDTO(UserApp user) {
		String email = user.getEmail();
	  
		return new UserContactDTO (email);
	}*/
}
