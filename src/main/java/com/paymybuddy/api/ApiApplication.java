package com.paymybuddy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.repository.IAccountRepository;
import com.paymybuddy.api.repository.IContactRepository;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.UserAccount;

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserAccount userAccount ;
	
	@Autowired
	IContactRepository contactRepository;

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
     @Override
	public  void run (String...args) {
    	
    	// userAccount.addUserAccount("testuser2@gmail.com");
    /*	Contact userContact = new Contact();
      	userContact.setIdContact("testuser1@gmail.com");
    	userContact.setFirstName("firstname1");
    	userContact.setLastName("lastname1");*/
    	
    	//userAccount.addUserContact("testuser1@gmail.com","testuser2@gmail.com");
    	//userAccount.addUserContact("testuser3@gmail.com","testuser2@gmail.com");
    	//UserApp user= userAccount.getUserEntityByEmail("testuser3@gmail.com");
    	// userAccount.findUserContacts("testuser2@gmail.com");
    	//UserApp userTest2=userAccount.getUserEntityByEmail("testuser2@gmail.com");
   // BuddyAccount  accountApp = new BuddyAccount(userTest2,7867842.4475);
    	
     	//BankAccount  bankAccount = new BankAccount(100824,"testuser2@gmail.com",787378.0124);
    	//System.out.println(accountApp);
    	//accountRepository.save(accountApp);
    	 //bankAccountRepository.save(bankAccount);
    	 //accountRepository.findByUser("testuser2@gmail.com");
    	Account  bankAccountUser2 =new Account();
    	bankAccountUser2.setId(100824);
    	bankAccountUser2.setUser(userRepository.findByEmail("testuser2@gmail.com"));
    	bankAccountUser2.setType("Banking Account");
    	bankAccountUser2.setBalance(546486.45);
    	accountRepository.save(	bankAccountUser2);
    	
    	Account  bankAccountUser3 =new Account();
    	bankAccountUser3.setId(100204);
    	bankAccountUser3.setUser(userRepository.findByEmail("testuser3@gmail.com"));
    	bankAccountUser3.setType("Banking Account");
    	bankAccountUser3.setBalance(10752);
    	accountRepository.save(	bankAccountUser3);
    	   	 
	}
}
