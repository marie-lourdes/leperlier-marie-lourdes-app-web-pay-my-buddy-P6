package com.paymybuddy.webapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.paymybuddy.webapp.controller.UserAccountController;
import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.DTO.UserLoginDTO;
import com.paymybuddy.webapp.domain.DTO.UserMapper;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.utils.ConstantsException;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAppService {
	private static final Logger log = LogManager.getLogger(UserAppService.class);

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper mapper;

	public UserApp createUser(UserApp userApp) {
		log.debug(" Creating user {}: ", userApp.getEmail());
		UserApp userCreated = new UserApp();
		UserApp userExisting = userRepository.findByEmail(userApp.getEmail());
		try {
			if (userExisting != null) {
				if (userExisting.getEmail().equals(userApp.getEmail())) {
					throw new IllegalArgumentException("Email " + userApp.getEmail() + " already exist!");
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
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error("Failed to create user  {}: ", userApp.getEmail());
		}

		log.debug("user created  successfully: {}", userCreated.getEmail());
		return userCreated;
	}

	public void addUserContact(String emailContact, String emailUser)
			throws IllegalArgumentException, NullPointerException {
		log.debug(" Creating contact {} of user {} ", emailContact, emailUser);

		UserApp user = new UserApp();
		UserApp contactToAdd = new UserApp();

		user = userRepository.findByEmail(emailUser);
		contactToAdd = userRepository.findByEmail(emailContact);// verifier que le contcat est bien utilisateur inscrit
		if (contactToAdd == null) {
			throw new NullPointerException("Contact not found: " + emailContact);
		} else if (user.getContacts().contains(contactToAdd)) {
			throw new IllegalArgumentException("Contact already exist!"+emailContact);
		} else {
			user.addContact(contactToAdd);
			userRepository.save(user);
			log.debug("Contact added  successfully: {}", emailContact);
		}
	}

	public UserLoginDTO getUserLoginByEmail(String email) throws NullPointerException {
		UserApp user = userRepository.findByEmail(email);
		UserLoginDTO userLoginDTO = mapper.userToUserLoginDTO(user);
		if (userLoginDTO == null) {
			throw new NullPointerException(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		System.out.println(userLoginDTO);
		return userLoginDTO;
	}

	public UserDTO getUserByEmail(String email) throws NullPointerException {
		UserApp user = userRepository.findByEmail(email);
		UserDTO userDTO = mapper.userToUserDTO(user);
		if (userDTO == null) {
			throw new NullPointerException(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		return userDTO;
	}

	public UserApp getUserEntityByEmail(String email) throws NullPointerException {
		UserApp user = userRepository.findByEmail(email);

		if (user == null) {
			throw new NullPointerException(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		return user;
	}

	public List<UserApp> getAllUserContacts(String emailUser) throws NullPointerException {
		log.debug(" Retrieving all contacts of Userof user {} ",  emailUser);
		UserApp user = userRepository.findByEmail(emailUser);
		if (user == null) {
			throw new NullPointerException(ConstantsException.USER_NULL_EXCEPTION + emailUser);
		}
		List<UserApp> userContacts = user.getContacts();
		if (userContacts.isEmpty()) {
			throw new NullPointerException("Contacts not found" + userContacts);
		}
		log.debug("Contacts retrieved successfully of user  {}", emailUser);
		return userContacts;
	}
}