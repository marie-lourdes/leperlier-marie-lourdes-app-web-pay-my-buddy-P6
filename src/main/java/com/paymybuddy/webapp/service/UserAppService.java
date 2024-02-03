package com.paymybuddy.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.DTO.UserLoginDTO;
import com.paymybuddy.webapp.domain.DTO.UserMapper;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IUserRepository;

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
			userCreated.setEmail(userApp.getEmail());
			userCreated.setFirstName(StringUtils.capitalize(userApp.getFirstName()));
			userCreated.setLastName(StringUtils.capitalize(userApp.getLastName()));
			String userPassword = userApp.getPassword();
			String userPasswordEncoded = passwordEncoder.encode(userPassword);
			userCreated.setPassword(userPasswordEncoded);
		
			userRepository.save(userCreated);
		}

		return userCreated;
	}

	public void addUserContact(String emailContact, String emailUser) throws IllegalArgumentException {
		UserApp user = new UserApp();
		UserApp contactToAdd = new UserApp();

		user = userRepository.findByEmail(emailUser);
		contactToAdd = userRepository.findByEmail(emailContact);// verifier que le contcat est bien utilisateur inscrit
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
		UserLoginDTO userLoginDTO = mapper.userToUserLoginDTO(user);
		if (userLoginDTO == null) {
			throw new NullPointerException("User " + user + "doesn't exist");
		}
		System.out.println(userLoginDTO);
		return userLoginDTO;
	}

	public UserDTO getUserByEmail(String email) throws NullPointerException {
		UserApp user = userRepository.findByEmail(email);
		UserDTO userDTO = mapper.userToUserDTO(user);
		if (userDTO == null) {
			throw new NullPointerException("User " + user + "doesn't exist");
		}
		return userDTO;
	}

	public UserDTO getUserEntityById(long id) {
		UserApp user = userRepository.findById(id).get();
		UserDTO userDTO = mapper.userToUserDTO(user);
		if (userDTO == null) {
			throw new NullPointerException("User " + userDTO + "doesn't exist");
		}
		return userDTO;
	}

	public List<UserApp> getAllUserContacts(String emailUser) {
		UserApp user = userRepository.findByEmail(emailUser);
		if (user == null) {
			throw new NullPointerException("User " + user + "doesn't exist");
		}
		List<UserApp> userContacts = user.getContacts();

		// System.out.println("userContact" + userContacts);
		return userContacts;
	}
}