package com.paymybuddy.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public Iterable<Transaction> getTransactionsByCreditUser(UserApp creditUser) {
		return transactionRepository.findByCreditUser(creditUser);
	}

	/*public Transaction getTransactionsByAccountId(long accountCreditUserId) {
		return transactionRepository.findById(accountCreditUserId).get();
	}*/
/*	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}*/
		public void addTransactionUserAndContact(Transaction transactionCreated) throws IllegalArgumentException {
		UserApp creditUser = userRepository.findByEmail(transactionCreated.getCreditUser().getEmail());
		UserApp beneficiaryUser= userRepository.findByEmail(transactionCreated.getBeneficiaryUser().getEmail());
		Transaction transactionAdd = new Transaction();
		
		List<Transaction> transactionsOfUserAccount = (List<Transaction>) this.getTransactionsByCreditUser( creditUser );
	
		
		if(transactionsOfUserAccount.contains(transactionCreated)) {
			throw new IllegalArgumentException("transaction already send and created");
		
		}else if (beneficiaryUser==null) {
			throw new IllegalArgumentException("Incorrect accountContact  provided: ");
		} else {
			creditUser.addTransaction(transactionCreated);
			transactionRepository.save(transactionCreated);
			userRepository.save(creditUser );
			
		}
		System.out.println("transactionCreated"+transactionCreated);
		
		/* if( userAccount.getClass() == BuddyAccount.class) {
			 userAccount= new BuddyAccount();
		 }else {
			 userAccount= new BankingAccount();
		 }*/
	
	}
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

