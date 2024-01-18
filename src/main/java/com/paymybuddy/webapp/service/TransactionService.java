package com.paymybuddy.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.ITransactionRepository;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.utils.IOperation;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService implements IOperation{
	
	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	UserAppService userAppService;

	@Autowired
	AccountService accountService;

	@Autowired
	IUserRepository userRepository;

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
	
	public void tranfer(String nameBeneficiary, String creditUserId, double amount) {
		UserApp usercontact = userAppService.findOneUserContactsByName(nameBeneficiary, creditUserId);
		UserApp creditUser = userAppService.getUserEntityByEmail(creditUserId);

		double balanceBeneficiary = accountService.findBuddyAccountByUser(usercontact.getEmail()).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(creditUserId).getBalance();
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amount);

		accountService.updateBalanceBuddyAccount(usercontact.getEmail(), balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(creditUser.getEmail(), balanceCalculatedCreditUser);

		Transaction transactionCreated = new Transaction(new Date(), creditUser, usercontact, "texte description",
				547.00, 0);
		this.saveTransactionBDD(transactionCreated);
	}

//calcul des comptes -tranfert
	
	public double addAmount(double balanceCreditUser, double payment) {	
		return add(balanceCreditUser, payment);
	}

	public double withdrawAmount(double balanceBeneficiaryUser, double payment) {
		return withdraw(balanceBeneficiaryUser, payment);
	}
	
	@Override
	public  double add(double balance, double amount){
		return balance + amount;
	}
	
	@Override
	public  double withdraw(double balance, double amount){
		return balance - amount;
	}
	
/* calcul frais de transaction
	public double calculateFees(double amountTransaction) {
		return 0;
	}*/
}
