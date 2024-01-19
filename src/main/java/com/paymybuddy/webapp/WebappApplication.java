package com.paymybuddy.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class WebappApplication implements CommandLineRunner {

	@Autowired
	UserAppService userAppService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	BankingService bankingService;

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) {

		//bankingService.payToContact("firstnameuser3", "testuser2@gmail.com", 25.00, "paiement au contact firstnameuser3");
		/*List<Transaction> transactionwithContactFoundByUser = transactionService
				.getTransactionsByCreditUser(userAppService.getUserEntityByEmail("testuser2@gmail.com"));
		System.out.println(" transaction with Contact Found By User" +  transactionwithContactFoundByUser);*/
		//bankingService.transferMoneyToBankingAccountUser("testuser2@gmail.com",100.00, "versement sur compte bancaire");
		/*List<Transaction> transactionFoundByUser = transactionService
				.getTransactionsByCreditUser(userAppService.getUserEntityByEmail("testuser2@gmail.com"));
		System.out.println("transaction Banking AccountUser" + transactionFoundByUser);*/
	}
}