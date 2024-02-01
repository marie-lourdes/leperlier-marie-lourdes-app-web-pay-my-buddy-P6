package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

@Service
public class BankingService {
	@Autowired
	@Qualifier("operationImpl")
	private IOperation operation;
	
	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;
	
	@Autowired
	private PaymentStrategy payment;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		
		payment.pay(emailCreditUser, emailBeneficiaryUser, amount);
		
		UserDTO userContact = userAppService.getUserByEmail(emailBeneficiaryUser);
		UserDTO creditUser = userAppService.getUserByEmail(emailCreditUser);
		transactionService.addTransaction(creditUser.getId(),emailCreditUser, transactionCreated);
		
	}

	// Mise à jour des comptes crediteur et beneficiare

	public void transferMoneyToBankingAccountUser(String userEmail, double amount,
			String description, Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		try {
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userEmail).getBalance();
			if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}

			payment.pay(userEmail, amount, Constants.BANKING_ACCOUNT);
			
			UserDTO user = userAppService.getUserByEmail(userEmail);
			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// !!!regrouper les deux méthodes qui ont quasiment la meme implementation sauf
	// l appel de la method acourndService.findbuddyaccount, et findBankingAccount

	public void transferMoneyToBuddyAccountUser(String userEmail, double amount,
			String description, Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		try {
			double userBankingAccountBalance = accountService.findBankingAccountByUser(userEmail).getBalance();
			if (isPaymentAuthorized(amount, userBankingAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}

			payment.pay(userEmail, amount, Constants.BUDDY_ACCOUNT);
			
			UserDTO user = userAppService.getUserByEmail(userEmail);
			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour du compte buddy de l utilisateur et compte bancaire du meme
	// utilisateur

	

	public Page<Transaction> getTransactionsByUser(int pageNber, int pageSize, String email) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(pageNber - 1, pageSize, sort);
		return transactionService.findTransactionsPaginatedByUser(email, pageable);
	}
	
/*-----------dans les class payments impl----------------*/
	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return operation.isOperationAuthorized(payment, userAccountBalance);
	}
	
	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}
}
