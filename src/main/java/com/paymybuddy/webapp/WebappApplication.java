package com.paymybuddy.webapp;

import java.util.List;

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
		/*UserApp userContact = userAppService.getUserEntityByEmail("testuser3@gmail.com");
		UserApp user = userAppService.getUserEntityByEmail("testuser2@gmail.com");
		Transaction transactionCreated= new Transaction();
		transactionCreated.setBeneficiaryUser(userContact);
		transactionCreated.setAmount(25.00);
		transactionCreated.setDescription("versement");
		transactionService.addTransactionUserAndContact(user.getEmail(),userContact.getEmail(), transactionCreated);*/
		
		UserApp user = userAppService.getUserEntityByIdl(2);
		List<Transaction>userTransaction =user.getTransactions();
		//System.out.println("user" + user.getTransactions());
		for(Transaction transaction:userTransaction) {
			System.out.println("transaction " + transaction.getBeneficiaryUser());
		}
/*List<Transaction> transactions = transactionService.getTransactionsByCreditUser( userAppService.getUserEntityByEmail("testuser2@gmail.com"));
for(Transaction transaction:transactions) {
	System.out.println("transaction" + transaction.getCreditUser().getEmail());
} */
		
	}
}