package com.paymybuddy.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.ContactDTO;
import com.paymybuddy.api.domain.DTO.ContactMapper;
import com.paymybuddy.api.domain.DTO.UserDTO;
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
	@Autowired
	private ContactMapper mapperContact;

	public UserApp createUser(UserApp userApp) {
		String userPassword = userApp.getPassword();
		String userPasswordEncoded = passwordEncoder.encode(userPassword);
		userApp.setPassword(userPasswordEncoded);
		return userRepository.save(userApp);
	}

	public UserLoginDTO subscribeUser(UserApp userApp) {
		String userPassword = userApp.getPassword();
		String userPasswordEncoded = passwordEncoder.encode(userPassword);
		userApp.setPassword(userPasswordEncoded);
		UserLoginDTO userLoginDTO = mapper.UserToUserLoginDTO(userApp);
		 userRepository.save(userApp);
		 return userLoginDTO;
	}
	
	public void addUserContact(String emailContact, String emailUser) {
		UserApp user = new UserApp();
		UserApp contact = new UserApp();
		Contact newUserContact = new Contact();
		try {
			user = userRepository.findByEmail(emailUser);
			contact = userRepository.findByEmail(emailContact);
			if (contact == null) {
				throw new NullPointerException("contact email " + emailContact + "not found");
			} else {
				newUserContact.setIdContact(contact.getEmail());
				newUserContact.setFirstName(contact.getFirstName());
				newUserContact.setLastName(contact.getLastName());
				newUserContact.setUser(user);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		contactRepository.save(newUserContact);
	}

	public List<ContactDTO> findUserContacts(String emailUser) {
		Iterable<Contact> allcontacts = contactRepository.findAll();
		List<ContactDTO> contacts =new ArrayList<ContactDTO>();
		
		allcontacts.forEach(contact->{
			ContactDTO contactAdded = mapperContact.contactToContactDTO(contact);
			if(contact.getUser().getEmail().equals(emailUser)) {
				//System.out.println("contact of user "+contact.getUser().getFirstName()+":"+contact.getIdContact() );
				contactAdded.setIdContact(contact.getIdContact());
				contactAdded.setFirstName(contact.getFirstName());
				contactAdded.setLastName(contact.getLastName());
				 contacts.add(contactAdded) ;
				
			}
			
			
			}) ;
		//System.out.println(allcontacts );//provoque des erreurs stackoverflow
		 return  contacts;
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
	

}
