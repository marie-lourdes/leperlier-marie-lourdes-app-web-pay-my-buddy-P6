package com.paymybuddy.api.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.api.domain.model.UserApp;

@Component
public class UserMapper {
	public UserApp DTOtoUser(UserLoginDTO userDTO) {
        return new UserApp(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRoles());
    }
}
