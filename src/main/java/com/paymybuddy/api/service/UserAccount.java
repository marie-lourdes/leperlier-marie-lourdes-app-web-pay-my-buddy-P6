package com.paymybuddy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.DTO.UserMapper;
import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IContactRepository;
import com.paymybuddy.api.repository.IUserRepository;

import jakarta.transaction.Transactional;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAccount {

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IContactRepository contactRepository;

	@Autowired
	private UserMapper mapper;

	public UserApp createUser(UserApp userApp) {
		String userPassword = userApp.getPassword();
		String userPasswordEncoded = passwordEncoder.encode(userPassword);
		userApp.setPassword(userPasswordEncoded);
		return userRepository.save(userApp);
	}

	public void addUserContact(String emailContact, String emailUser) {
		UserApp user = userRepository.findByEmail(emailUser);
		UserApp contact = userRepository.findByEmail(emailContact);
		Contact newUserContact = new Contact();
		newUserContact.setIdContact(contact.getEmail());
		newUserContact.setFirstName(contact.getFirstName());
		newUserContact.setLastName(contact.getLastName());
		newUserContact.setUser(user);
		// contact.setUser(user);
		contactRepository.save(newUserContact);

	}

	public List<Contact> findUserContacts() {
		Iterable<Contact> allcontacts = contactRepository.findAll();
		System.out.println(allcontacts );//provoque des erreurs stackoverflow
		return (List<Contact>) allcontacts;
	}

	public UserLoginDTO getUserLoginByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserLoginDTO userLoginDTO = mapper.UserToUserLoginDTO(user);
		System.out.println(userLoginDTO);
		return userLoginDTO;
	}

	/*public UserDTO getUserByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserDTO userDTO = mapper.UserToUserDTO(user);
		System.out.println(userDTO );
		return userDTO;
	}*/
	

}
