package com.paymybuddy.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.ITransactionRepository;
import com.paymybuddy.webapp.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	private IUserRepository userRepository;

	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public List<Transaction> getTransactionsByAccountCreditUser(Account accountCreditUser) {
		return transactionRepository.findByAccountCreditUser(accountCreditUser);
	}

/*	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}*/
	
	public Transaction addTransactionUserAndContact(long accountId,Transaction transactionCreated) throws IllegalArgumentException {
		
		Transaction transactionAdd = new Transaction();
		Account userAccount=  accountRepository.findById(accountId).get();
		List<Transaction> transactionsOfUserAccount = this.getTransactionsByAccountCreditUser(userAccount);
		Account beneficiaryAccount=transactionCreated.getAccountBeneficiaryUser();
		
		if(transactionsOfUserAccount.contains(transactionCreated)) {
			throw new IllegalArgumentException("transaction already send and created");
		
		}else if (beneficiaryAccount==null) {
			throw new IllegalArgumentException("Incorrect accountContact  provided: ");
		} else {
			userAccount.addTransaction(transactionCreated);
			accountRepository.save(userAccount);
			transactionRepository.save(transactionCreated);
		}
		return transactionCreated;
		/* if( userAccount.getClass() == BuddyAccount.class) {
			 userAccount= new BuddyAccount();
		 }else {
			 userAccount= new BankingAccount();
		 }*/
	
	}
	// method instead use constructor too much args
	
	/*public Transaction createTransaction(UserApp creditUser, UserApp contact,
			String description, Double amount, float transactionFees) {
		Transaction tranfertRegistered = new Transaction();
		tranfertRegistered.setDate(new Date());
		tranfertRegistered.setCreditUser(creditUser);
	
		tranfertRegistered.setBeneficiaryUser(contact);
		//tranfertRegistered.setBeneficiaryAccount(accountContact);
		tranfertRegistered.setDescription(description);
		tranfertRegistered.setAmount(amount);
		tranfertRegistered.setTransactionFees(transactionFees);
		return tranfertRegistered;
	}*/
	

	
/* calcul frais de transaction
	public double calculateFees(double amountTransaction) {
		return 0;
	}*/
}
