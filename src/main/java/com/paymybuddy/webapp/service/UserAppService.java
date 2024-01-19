package com.paymybuddy.webapp.service;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.DTO.UserLoginDTO;
import com.paymybuddy.webapp.domain.DTO.UserMapper;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IUserRepository;

import jakarta.transaction.Transactional;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAppService {

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper mapper;

	public UserApp createUser(UserApp userApp) throws IllegalArgumentException {
		UserApp userCreated = new UserApp();
		UserApp userExisting = userRepository.findByEmail(userApp.getEmail());

		if (userExisting != null) {
			if (userExisting.getEmail().equals(userApp.getEmail())) {
				throw new IllegalArgumentException("Email already exist!");
			}
		} else {
			String userPassword = userApp.getPassword();
			String userPasswordEncoded = passwordEncoder.encode(userPassword);
			userApp.setPassword(userPasswordEncoded);
			userCreated = userRepository.save(userApp);
		}

		return userCreated;
	}

	public void addUserContact(String emailContact, String emailUser) throws IllegalArgumentException {
		UserApp user = new UserApp();
		UserApp contactToAdd = new UserApp();

		user = userRepository.findByEmail(emailUser);
		contactToAdd = userRepository.findByEmail(emailContact);
		if (contactToAdd == null) {
			throw new IllegalArgumentException("Incorrect contact email  provided: " + emailContact);
		} else if (user.getContacts().contains(contactToAdd)) {
			throw new IllegalArgumentException("Contact already exist!");
		} else {
			user.addContact(contactToAdd);
			userRepository.save(user);
		}
	}

	
	public UserLoginDTO getUserLoginByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserLoginDTO userLoginDTO = mapper.UserToUserLoginDTO(user);
		System.out.println(userLoginDTO);
		return userLoginDTO;
	}

	public UserDTO getUserByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserDTO userDTO = mapper.UserToUserDTO(user);
		return userDTO;
	}

	public UserApp getUserEntityByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		return user;
	}
	
	public UserApp getUserEntityByName(String firstName, String lastName) {
		UserApp user = userRepository.findByFirstNameAndLastName(firstName, lastName);
		return user;
	}

	public List<UserApp> findAllUserContacts(String emailUser) {
		UserApp user = userRepository.findByEmail(emailUser);
		List<UserApp> userContacts = user.getContacts();
		System.out.println("userContact" + userContacts);
		return userContacts;
	}
	
/*	public UserApp findOneUserContactsByName(String nameContact, String emailUser) {
	Stream<UserApp> contactUser = this.findAllUserContacts(emailUser).stream().filter((elem) -> {
		return elem.getFirstName().equals(nameContact);
	});
	List<UserApp> usercontact = contactUser.collect(Collectors.toList());
	return usercontact.get(0);
	}*/
}
