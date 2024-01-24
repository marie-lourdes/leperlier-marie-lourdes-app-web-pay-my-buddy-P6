package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.Billing;
import com.paymybuddy.webapp.utils.IOperation;

@Service
public class BankingService implements IOperation {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailContact, String emailUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException{

		double userBuddyAccountBalance = accountService.findBuddyAccountByUser(emailUser).getBalance();
		if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
			throw new IllegalArgumentException("balance/amount of transaction is negative");
		}
		UserApp userContact = userAppService.getUserEntityByEmail(emailContact);
		UserApp creditUser = userAppService.getUserEntityByEmail(emailUser);
		String userContactEmail = userContact.getEmail();

		
			double feesTransaction = updateBalanceContactAndBalanceCreditUserWithFeesTransaction(userContactEmail,
					emailUser, amount);

			transactionService.addTransactionUserAndContact(creditUser.getId(), userContact.getEmail(), transactionCreated);
		

	}

	// Mise à jour des comptes crediteur et beneficiare

	public double updateBalanceContactAndBalanceCreditUserWithFeesTransaction(String contactEmail, String emailUser,
			double amount) {
		double feesTransaction =0;
		try {
		double balanceBeneficiary = accountService.findBuddyAccountByUser(contactEmail).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(emailUser).getBalance();
		System.out.println("balanceCredit " + balanceCredit);
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
	    feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = addAmount(amount, feesTransaction);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
		System.out.println("balanceCalculatedCreditUser " + balanceCalculatedCreditUser);
		accountService.updateBalanceBuddyAccount(accountService.findBuddyAccountByUser(contactEmail).getUser().getId(),
				balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
				balanceCalculatedCreditUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return feesTransaction;
	}

	public void transferMoneyToBankingAccountUser(String userEmail, String contactEmail, double amount,
			String description) {
		try {
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userEmail).getBalance();
			if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}

			UserApp user = userAppService.getUserEntityByEmail(userEmail);

			double feesTransaction = updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(
					accountService.findBuddyAccountByUser(contactEmail).getUser().getEmail(), amount);
			/*Transaction transactionCreated = new Transaction(new Date(), user,  userAppService.getUserEntityByEmail(userId), description,
					amount, feesTransaction);*/

			//transactionService.addTransactionUserAndContact( userId,contactId,transactionCreated);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour du compte buddy de l utilisateur et compte bancaire du meme
	// utilisateur

	public double updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String emailUser, double amount) throws Exception {
		double feesTransaction =0;
		try {
			double balanceBuddyAccount = accountService.findBuddyAccountByUser(emailUser).getBalance();
			double balanceBankingAccount = accountService.findBankingAccountByUser(emailUser).getBalance();
		feesTransaction = Billing.calculateFees(amount);
			double amountWithFeesTransaction = addAmount(amount, feesTransaction);
			double balanceCalculatedBeneficiaryUser = addAmount(balanceBankingAccount, amount);
			double balanceCalculatedCreditUser = withdrawAmount(balanceBuddyAccount, amountWithFeesTransaction);

			accountService.updateBalanceBankingAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
					balanceCalculatedBeneficiaryUser);
			accountService.updateBalanceBuddyAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
					balanceCalculatedCreditUser);
		}catch(Exception e) {
			e.getMessage();
		}
		return feesTransaction;
	}

	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return isOperationAuthorized(payment, userAccountBalance);
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
	public boolean isOperationAuthorized(double amount, double userAccountBalance) {
		boolean isAuthorized = false;
		if (userAccountBalance <= 0 || amount <= 0) {
			isAuthorized = true;
		}
		return isAuthorized;
	}
}
