package com.paymybuddy.webapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.IOperation;

@Service
public class BankingService implements IOperation {
	@Autowired
	TransactionService transactionService;

	@Autowired
	UserAppService userAppService;

	@Autowired
	AccountService accountService;

	
	public void payToContact(String contactName, String creditUserId, double amount) {
		UserApp usercontact = userAppService.findOneUserContactsByName(contactName, creditUserId);
		UserApp creditUser = userAppService.getUserEntityByEmail(creditUserId);

		double balanceBeneficiary = accountService.findBuddyAccountByUser(usercontact.getEmail()).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(creditUserId).getBalance();
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amount);

		accountService.updateBalanceBuddyAccount(usercontact.getEmail(), balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(creditUser.getEmail(), balanceCalculatedCreditUser);

		Transaction transactionCreated = new Transaction(new Date(), creditUser, usercontact, "texte description",
				547.00, 0);
		transactionService.saveTransactionBDD(transactionCreated);
	}

	public void transfertToBuddyAccountUser(Account bankingAccount, Account buddyAccount, double amount) {}
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
}
