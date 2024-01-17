package com.paymybuddy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.repository.ITransactionRepository;
import com.paymybuddy.api.repository.IUserRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	IUserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
@Transactional
	@Override
	public void run(String... args) {
		Transaction transaction =transactionRepository.findByCreditUser(userRepository.findByEmail("testuser2@gmail.com"));
		System.out.println(transaction);
	}
}
