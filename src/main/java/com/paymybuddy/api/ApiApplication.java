package com.paymybuddy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.repository.IAccountRepository;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.banking.TransactionService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IAccountRepository accountRepository;
	
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

	}
}
