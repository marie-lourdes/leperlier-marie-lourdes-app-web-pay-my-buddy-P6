package com.paymybuddy.api.service.banking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.ITransactionRepository;
import com.paymybuddy.api.service.UserAppService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {
	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	UserAppService userAppService;

	@Autowired
	AccountService accountService;

	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public List<Transaction> getTransactionsByCreditUser(UserApp creditUser) {
		return transactionRepository.findByCreditUser(creditUser);
	}

	public void saveTransactionBDD(Transaction transaction) {
		transactionRepository.save(transaction);

	}

	// method instead use constructor too much args
	public Transaction createTransaction(UserApp creditUser, Account accountCreditUser, UserApp contact,
			Account accountContact, String description, Double amount, float transactionFees) {
		Transaction tranfertRegistered = new Transaction();
		tranfertRegistered.setDate(new Date());
		tranfertRegistered.setCreditUser(creditUser);
		tranfertRegistered.setCreditAccount(accountCreditUser);
		tranfertRegistered.setBeneficiaryUser(contact);
		tranfertRegistered.setBeneficiaryAccount(accountContact);
		tranfertRegistered.setDescription(description);
		tranfertRegistered.setAmount(amount);
		;
		tranfertRegistered.setTransactionFees(transactionFees);
		return tranfertRegistered;
	}
	
	public void tranfer(String nameBeneficiary, String creditUserId, double amount) {
		UserApp usercontact = userAppService.findOneUserContactsByName(nameBeneficiary, creditUserId);
		UserApp creditUser = userAppService.getUserEntityByEmail(creditUserId);
		Account accountCreditUser = accountService.findBuddyAccountByUser(creditUserId);
		Account accountUserContact = accountService.findBuddyAccountByUser(usercontact.getEmail());

		double balanceBeneficiary = accountService.findBuddyAccountByUser(usercontact.getEmail()).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(creditUserId).getBalance();
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amount);

		// Stream<Object> resultMapContactUser= contactUser.map(elem->elem);
		System.out.println("filter contact firstname" + usercontact);

		Transaction transationCreated = this.createTransaction(
				creditUser, 
				accountService.findBuddyAccountByUser(creditUserId), 
				usercontact, 
				accountService.findBuddyAccountByUser(usercontact.getEmail()), 
				"texte description", 
				amount,
				0);	

		this.saveTransactionBDD(transationCreated);
//mise a jour compte
		accountUserContact=accountService.updateBalanceBuddyAccount(usercontact.getEmail(), balanceCalculatedBeneficiaryUser);
		accountCreditUser=accountService.updateBalanceBuddyAccount(creditUser.getEmail(), balanceCalculatedCreditUser);
		accountCreditUser.addTransaction(transationCreated);
		accountCreditUser=	 accountService.updateTransactionBuddyAccount(creditUserId, transationCreated);
	
		 
	}
	
//calcul des comptes -tranfert
	public double addAmount(double balance, double amount) {
		return balance - amount;
	}

	public double withdrawAmount(double balance, double amount) {
		return balance + amount;
	}

}
