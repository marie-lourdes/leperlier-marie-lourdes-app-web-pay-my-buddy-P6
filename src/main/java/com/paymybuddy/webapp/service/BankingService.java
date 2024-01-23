package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.Billing;
import com.paymybuddy.webapp.utils.IOperation;

@Service
public class BankingService implements IOperation {
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	public void payToContact(String emailContact, String creditUserId, double amount,String description) {

		try {
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(creditUserId).getBalance();
		if (isPaymentAuthorized( amount,userBuddyAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}
			UserApp userContact = userAppService.getUserEntityByEmail(emailContact);
			UserApp creditUser = userAppService.getUserEntityByEmail(creditUserId);
			String userContactEmail = userContact.getEmail();

			double feesTransaction = updateBalanceContactAndBalanceCreditUserWithFeesTransaction(userContactEmail,
					creditUserId, amount);
		
	/*Transaction transactionCreated = new Transaction();

	 transactionCreated.setCreditUser(creditUser);

	 transactionCreated.setBeneficiaryUser( creditUser t);
	 transactionCreated.setBeneficiaryAccount(userContact);
	 transactionCreated.setDescription(description);
	 transactionCreated.setAmount(amount);
	transactionCreated.setTransactionFees( feesTransaction );*/

		//	transactionService.addTransactionUserAndContact(transactionCreated);
			//System.out.println("amount transaction"+amount);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour des comptes crediteur et beneficiare

	public double updateBalanceContactAndBalanceCreditUserWithFeesTransaction(String contactEmail, String creditUserId,
			double amount) {
		double balanceBeneficiary = accountService.findBuddyAccountByUser( contactEmail).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(creditUserId).getBalance();
		System.out.println("balanceCredit "+balanceCredit );
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction =addAmount(amount,   feesTransaction);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
		System.out.println("balanceCalculatedCreditUser "+balanceCalculatedCreditUser );
		accountService.updateBalanceBuddyAccount( contactEmail, balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(creditUserId, balanceCalculatedCreditUser);

		return feesTransaction;
	}

	public void transferMoneyToBankingAccountUser(String userId, String contactId,double amount,String description) {
		try {
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userId).getBalance();
			if (isPaymentAuthorized( amount,userBuddyAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}
			
			UserApp user = userAppService.getUserEntityByEmail(userId);
			
			double feesTransaction = updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(userId,
					amount);
			/*Transaction transactionCreated = new Transaction(new Date(), user,  userAppService.getUserEntityByEmail(userId), description,
					amount, feesTransaction);*/

			//transactionService.addTransactionUserAndContact( userId,contactId,transactionCreated);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Mise à jour du compte buddy de l utilisateur et compte bancaire du meme utilisateur

	public double updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String userId,
			double amount) {
		double balanceBuddyAccount = accountService.findBuddyAccountByUser(userId).getBalance();
		double balanceBankingAccount= accountService.findBankingAccountByUser(userId).getBalance();
		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction =addAmount(amount,   feesTransaction);
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBankingAccount,  amount);
		double balanceCalculatedCreditUser = withdrawAmount( balanceBuddyAccount, amountWithFeesTransaction);

		accountService.updateBalanceBankingAccount( userId, balanceCalculatedBeneficiaryUser);
		accountService.updateBalanceBuddyAccount(userId, balanceCalculatedCreditUser);

		return feesTransaction;
	}
	
	public boolean isPaymentAuthorized( double payment,double userAccountBalance) {
		return isOperationAuthorized( payment,userAccountBalance);
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
	public boolean isOperationAuthorized( double amount,double userAccountBalance ) {
		boolean isAuthorized = false;
		if (userAccountBalance <= 0 || amount <= 0) {
			isAuthorized = true;
		}
		return isAuthorized;
	}
}
