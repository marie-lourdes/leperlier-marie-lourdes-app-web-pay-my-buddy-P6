package com.paymybuddy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.BankAccount;
import com.paymybuddy.api.domain.model.BuddyAccount;
import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.repository.IBankAccountRepository;
import com.paymybuddy.api.repository.IBuddyAccountRepository;
import com.paymybuddy.api.repository.IContactRepository;
import com.paymybuddy.api.service.UserAccount;

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserAccount userAccount ;
	
	@Autowired
	IContactRepository contactRepository;
	
	@Autowired
	IBuddyAccountRepository accountRepository;
	
	@Autowired
	IBankAccountRepository BankAccountRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
     @Override
	public  void run (String...args) {
    	BuddyAccount  accountApp = new BuddyAccount("testuser3@gmail.com",0.0);
    	BankAccount  bankAccount = new BankAccount(100824,"testuser3@gmail.com",787378.0124);
    	System.out.println(accountApp);
    	Contact userContact = new Contact();
      	userContact.setIdContact("testuser1@gmail.com");
    	userContact.setFirstName("firstname1");
    	userContact.setLastName("lastname1");
    	
    	userAccount.addUserContact("testuser1@gmail.com","testuser2@gmail.com");
    	userAccount.addUserContact("testuser3@gmail.com","testuser2@gmail.com");
    	//UserApp user= userAccount.getUserEntityByEmail("testuser3@gmail.com");
    	 userAccount.findUserContacts("testuser2@gmail.com");
    	 
    	 accountRepository.save(accountApp);
    	 BankAccountRepository.save()
	}
}
