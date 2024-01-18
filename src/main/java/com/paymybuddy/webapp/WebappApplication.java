package com.paymybuddy.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.ITransactionRepository;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.service.AccountService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class WebappApplication implements CommandLineRunner {
	
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
		SpringApplication.run(WebappApplication.class, args);
	}
	
	@Transactional
	@Override
	public void run(String... args) {
		
		transactionService.tranfer("firstnameuser3", "testuser2@gmail.com",547.00);
		List<Transaction> transactionFountByUser=transactionService.getTransactionsByCreditUser(userAppService.getUserEntityByEmail("testuser2@gmail.com"));
		System.out.println(transactionFountByUser);
	}
}