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
import com.paymybuddy.api.domain.model.BuddyAccount;
import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IBuddyAccountRepository;
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
	private IBuddyAccountRepository accountRepository;

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private ContactMapper mapperContact;
	
	private Double accountFound;

	public UserApp createUser(UserApp userApp) {
		String userPassword = userApp.getPassword();
		String userPasswordEncoded = passwordEncoder.encode(userPassword);
		userApp.setPassword(userPasswordEncoded);
		return userRepository.save(userApp);
	}

	public void addUserContact(String emailContact, String emailUser) {
		UserApp user = new UserApp();
		UserApp contactToAdd = new UserApp();
		Contact newUserContact = new Contact();
		try {
			user = userRepository.findByEmail(emailUser);
			contactToAdd = userRepository.findByEmail(emailContact);
			if (contactToAdd== null) {
				throw new NullPointerException("contact email " + emailContact + "not found");
			} else {
				newUserContact.setIdContact(contactToAdd.getEmail());
				newUserContact.setFirstName(contactToAdd.getFirstName());
				newUserContact.setLastName(contactToAdd.getLastName());
				newUserContact.setUser(user);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		contactRepository.save(newUserContact);
	}

	public List<ContactDTO> findUserContacts(String emailUser) {
		Iterable<Contact> allContacts = contactRepository.findAll();
		List<ContactDTO> contacts =new ArrayList<ContactDTO>();
		
		allContacts.forEach(contact->{
			ContactDTO contactFound= mapperContact.contactToContactDTO(contact);
			if(contact.getUser().getEmail().equals(emailUser)) {
				//System.out.println("contact of user "+contact.getUser().getFirstName()+":"+contact.getIdContact() );
				contactFound.setIdContact(contact.getIdContact());
				contactFound.setFirstName(contact.getFirstName());
				contactFound.setLastName(contact.getLastName());
				contacts.add(contactFound) ;	
			}		
			}) ;
		//System.out.println(allcontacts );//provoque des erreurs stackoverflow
		 return  contacts;
	}
	
	public void addUserAccount(String emailUser) {
		UserApp user = new UserApp();
		BuddyAccount newAccount = new BuddyAccount();
		try {
			user = userRepository.findByEmail(emailUser);
			if (user == null) {
				throw new NullPointerException("user email " + emailUser + "not found");
			} else {
				newAccount.setBalance(0.0);
				newAccount.setType("Buddy Account");
				newAccount.setUser(user);	
			}
		} catch (Exception e) {
			e.getMessage();
		}
		accountRepository.save(newAccount);
	}
	public Double findAccountBalanceByUser(String emailUser) {
		Iterable<BuddyAccount >allAccounts=  accountRepository.findAll();
		 accountFound=0.0;
		//BuddyAccount newAccount = new BuddyAccount();
		allAccounts.forEach(account->{
		System.out.println("account solde"+account);
		if( account.getUser().getEmail().equals(emailUser)) {	
				 accountFound=account.getBalance();			
			}		
			}) ;
		return accountFound;
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
}
