package com.paymybuddy.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.service.PaymentService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAccountService;

@SpringBootApplication
public class WebappApplication  {
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}
}