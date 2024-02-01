package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

import lombok.Data;

@Data
@Component(value = "paymentContact")
public class PaymentContact implements IPayment {


	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private AccountService accountService;
	
	private double balanceCalculatedBeneficiaryUser ;

	private double balanceCalculatedCreditUser ;
	
	@Override
	public void pay(String emailCreditUser, String emailBeneficiaryUser,double amount) {
		double userBuddyAccountBalance = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();

		if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
			throw new IllegalArgumentException("balance/amount of transaction is negative");
		}
		
		this.calculBalance(emailCreditUser,  emailBeneficiaryUser, amount);
		
		try {
			this.updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(emailCreditUser, emailBeneficiaryUser);
		} catch (Exception e) {

			e.getMessage();
		}
	};

	public void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount) {
		balanceCalculatedBeneficiaryUser = 0;
		balanceCalculatedCreditUser =0;
		double balanceBeneficiary = accountService.findBuddyAccountByUser(emailBeneficiaryUser).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		System.out.println("balanceCredit " + balanceCredit);
		
		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = addAmount(amount, feesTransaction);
		this.balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		 this.balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
	}

	public void updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(String emailCreditUser,
			String emailBeneficiaryUser) throws Exception {

		if (accountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation() == null) {
			throw new NullPointerException("Buddy Account of contact user doesn't exist");
		}

		accountService.updateBalanceAccount(
				accountService.findBuddyAccountByUser(emailBeneficiaryUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBeneficiaryUser), Constants.BUDDY_ACCOUNT);
		accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(emailCreditUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedCreditUser), Constants.BUDDY_ACCOUNT);
	}

	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return operation.isOperationAuthorized(payment, userAccountBalance);
	}

	// Calcul des comptes -tranfert

	public double addAmount(double balanceCreditUser, double payment) {
		return operation.add(balanceCreditUser, payment);
	}

	public double withdrawAmount(double balanceBeneficiaryUser, double payment) {
		return operation.withdraw(balanceBeneficiaryUser, payment);
	}

	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}

}
