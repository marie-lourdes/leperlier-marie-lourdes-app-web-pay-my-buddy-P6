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
	//method instead use constructor too much args
	public void createTransaction(Date date,UserApp creditUser, Account accountCreditUser,UserApp contact, Account accountContact,String description,Double amount, float transactionFees) {
		Transaction tranfertRegistered =new Transaction ();
		tranfertRegistered.setDate(new Date());
		tranfertRegistered.setCreditUser(creditUser);
		tranfertRegistered.setCreditAccount(accountCreditUser);
		tranfertRegistered.setBeneficiaryUser( contact);
		tranfertRegistered.setBeneficiaryAccount(accountContact);
		tranfertRegistered.setDescription(description);
		tranfertRegistered.setTransactionFees(transactionFees);
	}
	
	public void tranfer(String nameBeneficiary, String creditUserId, double amount) {
		UserApp usercontact = userAppService.findOneUserContactsByName(nameBeneficiary, creditUserId);
		
		double balanceBeneficiary=accountService.findBuddyAccountByUser(usercontact.getEmail()).getBalance();
		double balanceCredit=accountService.findBuddyAccountByUser(creditUserId).getBalance();
		
		double	balanceCalculatedBeneficiaryUser =addAmount(balanceBeneficiary,amount);
		double balanceCalculatedCreditUser =withdrawAmount(balanceCredit, amount);
	
		accountService.updateBalanceBuddyAccount(usercontact.getEmail(),balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(usercontact.getEmail(),balanceCalculatedBeneficiaryUser);
		// Stream<Object> resultMapContactUser= contactUser.map(elem->elem);
				System.out.println("filter contact firstname" + usercontact);
				
				
	}
	
	public double addAmount(double balance,double amount) {
		return balance- amount;
	}
	public double withdrawAmount(	double balance,double amount) {
		return balance+ amount;
	}
	
}
