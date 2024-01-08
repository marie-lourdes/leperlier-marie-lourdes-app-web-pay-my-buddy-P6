package com.paymybuddy.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserDTO;
import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.DTO.UserMapper;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserRepository;

import jakarta.transaction.Transactional;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAccount {
	
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper mapper;
	
	public UserApp createUser(UserApp userApp) {
		String userPassword =userApp.getPassword();
		String userPasswordEncoded=passwordEncoder.encode(userPassword);
		userApp.setPassword(userPasswordEncoded);
		return userRepository.save(userApp);
	}
	
	public UserApp  findByEmailAdress(String  email) {
	
		UserApp userEmailAdress =userRepository.findByEmailAddress(email);
		System.out.println("userEmailAdress"+userEmailAdress);
		return userEmailAdress;
		
	}
	
	public void addUserContact(List<UserApp>contacts, String email) {

	    userRepository.setContacts( contacts, email );
		
	}

	/*public void addUserContact(String  email,String  emailContact) {
		UserApp userContact = userRepository.findByEmail(emailContact).get();
		//UserContactDTO userContactDTO = mapper.UserToUserContactDTO(userContact);
		UserApp user = userRepository.findByEmail(email).get();
		List<UserApp> contactsOfUser=user.getContacts();
		contactsOfUser.add(userContact );
		user.setContacts(contactsOfUser);
		System.out.println("user contact"+userContact);
		System.out.println("user :"+user);
		userRepository.save(user);
	}*/
	
	public UserApp getUserEntityByEmail(String email) {
		UserApp userFound = userRepository.findByEmail(email).get();
		System.out.println("user entity By email :"+userFound);
		return userFound;
	}
	
	public UserLoginDTO getUserLoginByEmail(String email) {
		UserApp user = userRepository.findByEmail(email).get();
		UserLoginDTO userLoginDTO = mapper.UserToUserLoginDTO(user);
		System.out.println(userLoginDTO );
		return userLoginDTO;
	}
	
	public UserDTO getUserByEmail(String email) {
		UserApp user = userRepository.findByEmail(email).get();
		UserDTO userDTO = mapper.UserToUserDTO(user);
		System.out.println(userDTO );
		return userDTO;
	}
	

}
