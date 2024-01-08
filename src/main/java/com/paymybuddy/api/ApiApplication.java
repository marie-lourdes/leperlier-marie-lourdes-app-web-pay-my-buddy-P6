package com.paymybuddy.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.UserAccount;

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserAccount userAccount ;
	
	@Autowired
	private IUserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
     @Override
	public  void run (String...args) {
    	 
    	// List<Contact> contactsOfUser= userRegistered.getContacts();
    	Contact userContact = new Contact();
      	userContact.setIdContact("testuser1@gmail.com");
    	userContact.setFirstName("firstname1");
    	userContact.setLastName("lastname1");
  
    	//userContact.setUser(userRegistered);
 		//contactsOfUser.add(userContact);
       userAccount.addUserContact(userContact,userRegistered);
 userAccount.getUserEntityByEmail("testuser2@gmail.com");
    // UserApp  user2=userRepository.findByEmail("testuser1@gmail.com");
 // System.out.println("user contact ajouté"+user2); 
    	 
    	 //test method repository avec @query JPA
    	//userAccount.findByEmailAdress("testuser2@gmail.com") ;
    	 
   
    	/* userAccount.getUserLoginByEmail("testuser1@gmail.com");	 */
     	userAccount.getUserByEmail("testuser3@gmail.com");
    	
	}
}
