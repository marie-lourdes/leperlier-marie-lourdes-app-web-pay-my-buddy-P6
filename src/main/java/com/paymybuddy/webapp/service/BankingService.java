package com.paymybuddy.webapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.Billing;
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

		try {
			if (isPaymentAuthorized(creditUserId, amount)) {
				throw new Exception("balance/amount of transaction is negative");
			}
			UserApp usercontact = userAppService.findOneUserContactsByName(contactName, creditUserId);
			UserApp creditUser = userAppService.getUserEntityByEmail(creditUserId);
			String userContactEmail = usercontact.getEmail();

			double feesTransaction = updateBalanceContactAndBalanceUserWithFeesTransaction(userContactEmail,
					creditUserId, amount);
			Transaction transactionCreated = new Transaction(new Date(), creditUser, usercontact, "texte description",
					amount, feesTransaction);

			transactionService.saveTransactionDB(transactionCreated);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour des comptes crediteur et beneficiare

	public double updateBalanceContactAndBalanceUserWithFeesTransaction(String contactId, String creditUserId,
			double amount) {
		double balanceBeneficiary = accountService.findBuddyAccountByUser(contactId).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(creditUserId).getBalance();
System.out.println("---------balanceCredit ---"+balanceCredit );
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double amountWithFeesTransaction = Billing.calculateFees(amount);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
		System.out.println("---------balanceCreditcalculated ---"+balanceCalculatedCreditUser );
		accountService.updateBalanceBuddyAccount(contactId, balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(creditUserId, balanceCalculatedCreditUser);

		return amountWithFeesTransaction;
	}

	public void transfertToBuddyAccountUser(Account bankingAccount, Account buddyAccount, double amount) {

	}

	public boolean isPaymentAuthorized(String userId, double payment) {
		return isOperationAuthorized(userId, payment);
	}
	// Calcul des comptes -tranfert

	public double addAmount(double balanceCreditUser, double payment) {
		return add(balanceCreditUser, payment);
	}

	public double withdrawAmount(double balanceBeneficiaryUser, double payment) {
		return withdraw(balanceBeneficiaryUser, payment);
	}

	@Override
	public double add(double balance, double amount) {
		return balance + amount;
	}

	@Override
	public double withdraw(double balance, double amount) {
		return balance - amount;
	}

	@Override
	public boolean isOperationAuthorized(String userId, double amount) {
		double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userId).getBalance();
		boolean isAuthorized = false;
		if (userBuddyAccountBalance <= 0 || amount <= 0) {
			isAuthorized = true;
		}
		return isAuthorized;
	}

}
