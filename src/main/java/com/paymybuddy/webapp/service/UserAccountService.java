package com.paymybuddy.webapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.paymybuddy.webapp.AccountFactory;
import com.paymybuddy.webapp.AccountFactory.AccountType;
import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.DTO.UserLoginDTO;
import com.paymybuddy.webapp.domain.DTO.UserMapper;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.utils.ConstantsException;

//service creation user, avec page sign up pour le controller et utilise le service d authentification
@Transactional
@Service
public class UserAccountService {
	private static final Logger log = LogManager.getLogger(UserAccountService.class);

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper mapper;

	@Autowired
	@Qualifier("accountImpl")
	private IBalance account;

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
			throw new IllegalArgumentException("Contact already exist!" + emailContact);
		} else {
			user.addContact(contactToAdd);
			userRepository.save(user);
			log.debug("Contact added  successfully: {}", emailContact);
		}
	}

	public void addBuddyAccount(String emailUser) throws Exception {
		log.debug(" Creating Buddy Account {} of user {} ",  emailUser);
		BuddyAccount existingBuddyAccount = this.findBuddyAccountByUser(emailUser);

		if (existingBuddyAccount != null) {
			if (existingBuddyAccount.getUser().getEmail().equals(emailUser)) {
				throw new IllegalArgumentException("Buddy Account already exist!");
			}
		}
		account.addBuddyAccount(emailUser);
		log.debug("Buddy account  created  successfully for user : {}",  emailUser);
	}

	public void updateBalanceBuddyAccount(long id, double amount) throws NullPointerException {
		log.debug("Updating amount  Buddy Account of user ");
		try {
			account.updateBalance(id, amount, AccountFactory.makeAccount(AccountType.BUDDY));
		} catch (NullPointerException e) {
			throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION);
		} catch (Exception e) {
			log.error("Failed to update amount Buddy Account"+e.getMessage());
		}
		log.debug("Amount Buddy Account updated successfully");
	}

	public void updateBalanceBankingAccount(long id, double amount) throws NullPointerException {
		log.debug("Updating amount  Banking Account of user ");
		try {
			account.updateBalance(id, amount, AccountFactory.makeAccount(AccountType.BANKING));
		} catch (NullPointerException e) {
			throw new NullPointerException(ConstantsException.BANKING_ACCOUNT_NULL_EXCEPTION);
		} catch (Exception e) {
			log.error("Failed to update amount Buddy Account"+e.getMessage());
		}
		log.debug("Amount Banking Account updated successfully");
	}

	public BuddyAccount findBuddyAccountByUser(String emailUser) throws NullPointerException {
		log.debug(" Retrieving Buddy Account of user {} ", emailUser);
		BuddyAccount userBuddyAccount = null;

		try {
			userBuddyAccount = (BuddyAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BUDDY));
			if (userBuddyAccount == null) {
				throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION + " for " + emailUser);
			}
		} catch (Exception e) {
			log.debug(" Failed to retrieve Buddy Account of user {} " +e.getMessage(), emailUser);			
		}
		log.debug("Buddy Account retrieved successfully: {}",userBuddyAccount);
		return userBuddyAccount;
	}

	public BankingAccount findBankingAccountByUser(String emailUser) throws NullPointerException {
		log.debug(" Retrieving Banking Account of user {} ", emailUser);
		BankingAccount userBankingAccount = null;

		try {
			userBankingAccount = (BankingAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BANKING));
			if (userBankingAccount == null) {
				throw new NullPointerException(ConstantsException.BANKING_ACCOUNT_NULL_EXCEPTION + " for " + emailUser);
			}
		} catch (Exception e) {
			log.debug(" Failed to retrieve Banking Account of user {} " +e.getMessage(), emailUser);		
		}
		log.debug("Banking Account retrieved successfully: {}",userBankingAccount);
		return userBankingAccount;
	}

	public List<UserApp> getAllUserContacts(String emailUser) throws NullPointerException {
		log.debug(" Retrieving all contacts of user {} ", emailUser);
		UserApp user = userRepository.findByEmail(emailUser);
		if (user == null) {
			throw new NullPointerException(ConstantsException.USER_NULL_EXCEPTION + emailUser);
		}
		List<UserApp> userContacts = user.getContacts();
		if (userContacts.isEmpty()) {
			log.error("Contacts not found of user:{}", emailUser);
		}
		log.debug("Contacts retrieved successfully of user: {} ", emailUser);
		return userContacts;
	}

	public UserLoginDTO getUserLoginByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);
		UserLoginDTO userLoginDTO = mapper.userToUserLoginDTO(user);
		if (userLoginDTO == null) {
			log.error(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		System.out.println(userLoginDTO);
		return userLoginDTO;
	}

	public UserDTO getUserByEmail(String email)  {
		UserApp user = userRepository.findByEmail(email);
		UserDTO userDTO = mapper.userToUserDTO(user);
		if (userDTO == null) {
			log.error(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		return userDTO;
	}

	public UserApp getUserEntityByEmail(String email) {
		UserApp user = userRepository.findByEmail(email);

		if (user == null) {
			log.error(ConstantsException.USER_NULL_EXCEPTION + email);
		}
		return user;
	}

}