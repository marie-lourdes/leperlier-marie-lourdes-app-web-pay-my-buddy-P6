package com.paymybuddy.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IAccountRepository;
import com.paymybuddy.api.repository.ITransactionRepository;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.UserAppService;
import com.paymybuddy.api.service.banking.AccountService;
import com.paymybuddy.api.service.banking.TransactionService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
	
	@Autowired
	TransactionService transactionService;
	@Autowired
	UserAppService userAppService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	@Transactional
	@Override
	public void run(String... args) {
		//List<Transaction> transaction =transactionService.getTransactionsByCreditUser(userRepository.findByEmail("testuser2@gmail.com"));
		//System.out.println(transaction.size());
		transactionService.tranfer("firstnameuser3", "testuser2@gmail.com",547.00);
		//accountRepository.updateBalanceBuddyAccount(547.00, userRepository.findByEmail("testuser2@gmail.com"));
		
	/*	UserApp usercontact = userAppService.findOneUserContactsByName("firstnameuser3", "testuser2@gmail.com");
		UserApp creditUser = userAppService.getUserEntityByEmail("testuser2@gmail.com");
		Account accountCreditUser = accountService.findBuddyAccountByUser("testuser2@gmail.com");
		Account accountUserContact = accountService.findBuddyAccountByUser(usercontact.getEmail());
	Transaction transationCreated = transactionService.createTransaction(
				creditUser, 
				 accountCreditUser, 
				usercontact, 
				 accountUserContact, 
				"texte description", 
				547.00,
				0);*/
		//Transaction tranfertRegistered = new Transaction(new Date(),creditUser,accountCreditUser,accountUserContact,usercontact,"texte description",547.00,0);
		//transactionRepository.saveTransaction(new Date(),creditUser,accountCreditUser,accountUserContact,usercontact,"texte description",547.00,0);
		//transactionRepository.save(tranfertRegistered);
	}
}
