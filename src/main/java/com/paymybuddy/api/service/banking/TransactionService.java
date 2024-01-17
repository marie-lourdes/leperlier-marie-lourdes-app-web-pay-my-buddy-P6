package com.paymybuddy.api.service.banking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IAccountRepository;
import com.paymybuddy.api.repository.ITransactionRepository;
import com.paymybuddy.api.repository.IUserRepository;
import com.paymybuddy.api.service.UserAppService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {
	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	UserAppService userAppService;

	@Autowired
	AccountService accountService;
	
	@Autowired
	IUserRepository  userRepository ;

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
	public Transaction createTransaction(UserApp creditUser, UserApp contact,
			String description, Double amount, float transactionFees) {
		Transaction tranfertRegistered = new Transaction();
		tranfertRegistered.setDate(new Date());
		tranfertRegistered.setCreditUser(creditUser);
	//	tranfertRegistered.setCreditAccount(accountCreditUser);
		tranfertRegistered.setBeneficiaryUser(contact);
		//tranfertRegistered.setBeneficiaryAccount(accountContact);
		tranfertRegistered.setDescription(description);
		tranfertRegistered.setAmount(amount);
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
		
		accountService.updateBalanceBuddyAccount(usercontact.getEmail(), balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(creditUser.getEmail(), balanceCalculatedCreditUser);
		// Stream<Object> resultMapContactUser= contactUser.map(elem->elem);
		System.out.println("filter contact firstname" + usercontact);
		 accountCreditUser = accountService.findBuddyAccountByUser(creditUserId);
	     accountUserContact = accountService.findBuddyAccountByUser(usercontact.getEmail());
		//accountRepository.save(accountCreditUser);
		//accountRepository.save(accountUserContact);
		Transaction transationCreated = this.createTransaction(
				creditUser, 
				usercontact, 
				"texte description", 
				amount,
				0);

		this.saveTransactionBDD(transationCreated);	
		

		/*List<Transaction> listTransaction=accountCreditUser.getTransactions();
		 listTransaction.add(transationCreated);
	accountCreditUser.addTransaction(transationCreated);*/
	
			//accountService.updateTransactionBuddyAccount(creditUserId, listTransaction); //??????????
	//	this.saveTransactionBDD(transationCreated);
	
		
		//System.out.println("accountUserContact" + 	accountUserContact);
		//System.out.println("accountCreditUse" + 	accountCreditUser);
		//System.out.println("transationCreated" +transationCreated);
	}
	
//calcul des comptes -tranfert
	public double addAmount(double balance, double amount) {
		return balance + amount;
	}

	public double withdrawAmount(double balance, double amount) {
		return balance - amount;
	}

}
