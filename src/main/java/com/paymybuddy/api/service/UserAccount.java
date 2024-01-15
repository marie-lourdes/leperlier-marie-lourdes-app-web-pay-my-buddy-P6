package com.paymybuddy.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserDTO;
import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.DTO.UserMapper;
import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IAccountRepository;
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
	private IAccountRepository accountRepository;

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

	public void addUserContact(String emailContact, String emailUser) {
		UserApp user = new UserApp();
		UserApp contactToAdd = new UserApp();

		try {
			user = userRepository.findByEmail(emailUser);
			contactToAdd = userRepository.findByEmail(emailContact);
			if (contactToAdd == null) {
				throw new NullPointerException("contact email " + emailContact + "not found");
			} else {
				user.addContact(contactToAdd);
				;
				// newUserContact.setUserId(user.getEmail());
			}
		} catch (Exception e) {
			e.getMessage();
		}
		userRepository.save(user);

	}

	public void addBuddyAccount(String emailUser) {
		UserApp user = new UserApp();
		Account newAccount = new Account();
		try {
			user = userRepository.findByEmail(emailUser);
			if (user == null) {
				throw new NullPointerException("user email " + emailUser + "not found");
			} else {
				newAccount.setBalance(80.0);
				newAccount.setType("Buddy Account");
				newAccount.setUser(user);
				newAccount.setCreation(new Date());
			}
		} catch (Exception e) {
			e.getMessage();
		}
		accountRepository.save(newAccount);
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

	public List<UserApp> findUserContacts(String emailUser) {
		UserApp user = userRepository.findByEmail(emailUser);
		// List<UserApp> userContacts = new ArrayList<UserApp>();
		List<UserApp> userContacts = user.getContacts();

		System.out.println("userContact" + userContacts);
		return userContacts;
	}

	public Account findBuddyAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account buddyAccount = new Account();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getType().contains("Buddy Account")) {
				buddyAccount.setBalance(account.getBalance());
				System.out.println("account solde " + account);
			}
		});
		return buddyAccount;
	}

	public Account findBankingAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account bankingAccount = new Account();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getType().contains("Banking Account")) {
				bankingAccount.setBalance(account.getBalance());
				System.out.println("account solde " + account);
			}
		});
		return bankingAccount;
	}

}
