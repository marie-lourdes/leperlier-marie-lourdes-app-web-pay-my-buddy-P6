package com.paymybuddy.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.DTO.UserContactDTO;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.service.UserAccount;

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserAccount userAccount ;
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
     @Override
	public  void run (String...args) {
    	 //test userRegistered a executer une fois au vue des contrainte sur la suppression et mise a jour dans les tables
    	 UserApp userRegistered = new UserApp();
    	 userRegistered.setFirstName("firstname3");
    	 userRegistered.setLastName("lastname3");
    	 userRegistered.setEmail("testuser3@gmail.com");
    	 userRegistered.setPassword("codesecrettest");
    	 userRegistered.setRole("USER");
    	 userRegistered.setContacts(new ArrayList<UserApp>());
    	 userAccount.createUser(userRegistered);
    	userAccount.addUserContact("testuser1@gmail.com","testuser3@gmail.com");
    	 userAccount.getUserLoginByEmail("testuser3@gmail.com");
    	 
   
    	 userAccount.getUserLoginByEmail("testuser1@gmail.com");	 
     	userAccount.getUserByEmail("testuser1@gmail.com");
    	
	}
}
