package com.paymybuddy.api.service.banking;

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

	public void createTransaction(String nameBeneficiary, String creditUserId, double amount) {

	
		
		
	}
	public void tranfer(String nameBeneficiary, String creditUserId, double amount) {
		UserApp usercontact = userAppService.findOneUserContactsByName(nameBeneficiary, creditUserId);
		double balanceBeneficiary=accountService.findBuddyAccountByUser(usercontact.getEmail()).getBalance();
		double balanceCredit=accountService.findBuddyAccountByUser(creditUserId).getBalance();
		double balanceUpdatedCreditUser=addAmount(balanceBeneficiary,amount);
		double balanceUpdatedBeneficiaryUser=withdrawAmount(balanceCredit, amount);
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
