package com.paymybuddy.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

@SpringBootApplication
public class WebappApplication implements CommandLineRunner {

	@Autowired
	UserAppService userAppService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	BankingService bankingService;
	
	@Autowired
	IUserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) {
		/*UserApp user=userAppService.getUserEntityById(2);
		UserApp contact=userAppService.getUserEntityById(3);
		Transaction transactionCreated = new Transaction();
		transactionCreated.setBeneficiaryUser(contact);
		transactionCreated.setAmount(10);
		transactionCreated.setDescription("versement");
		 transactionService.addTransactionUserAndContact(2, contact.getEmail(), transactionCreated);*/
		
	}
}