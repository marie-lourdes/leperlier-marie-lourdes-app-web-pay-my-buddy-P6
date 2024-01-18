package com.paymybuddy.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Transaction;
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
		
		transactionService.tranfer("firstnameuser3", "testuser2@gmail.com",547.00);
		List<Transaction> transactionFountByUser=transactionService.getTransactionsByCreditUser(userAppService.getUserEntityByEmail("testuser2@gmail.com"));
		System.out.println(transactionFountByUser);
	}
}