package com.paymybuddy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.DTO.UserMapper;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserAppDTORepository;

import jakarta.transaction.Transactional;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAccount {

	@Autowired
	private IUserAppDTORepository userAppDTORepository;

	@Autowired
	private UserMapper mapper;

	public UserApp createUser(UserApp userApp) {
		return userAppDTORepository.save(userApp);
	}

	public UserLoginDTO getUserByEmail(String email) {
		UserApp user = userAppDTORepository.findByEmail(email);
		UserLoginDTO userDTO = mapper.UserToDTO(user);
		return userDTO;
	}
}
