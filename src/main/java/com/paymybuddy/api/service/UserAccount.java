package com.paymybuddy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.DTO.UserMapper;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserRepository;

import jakarta.transaction.Transactional;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAccount {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper mapper;

	public UserApp createUser(UserApp userApp) {
		return userRepository.save(userApp);
	}

	public UserLoginDTO getUserByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserLoginDTO userDTO = mapper.UserToDTO(user);
		System.out.println(userDTO );
		return userDTO;
	}
}
