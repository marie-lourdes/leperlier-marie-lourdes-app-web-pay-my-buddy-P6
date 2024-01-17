package com.paymybuddy.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.banking.TransactionService;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	IUserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) {
		List<Transaction> transaction =transactionService.getTransactionsByCreditUser(userRepository.findByEmail("testuser2@gmail.com"));
		System.out.println(transaction.size());
		transactionService.createTransaction("firstnameuser3", "testuser2@gmail.com");

	}
}
