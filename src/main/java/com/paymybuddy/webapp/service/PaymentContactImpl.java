package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

import lombok.Data;

@Data
@Component(value = "paymentContactImpl")
public class PaymentContactImpl implements IPayment {

	@Autowired
	@Qualifier("operationImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private UserAccountService userAccountService;

	private double balanceCalculatedBeneficiaryUser;

	private double balanceCalculatedCreditUser;

	@Override
	public void pay(String emailCreditUser, String emailBeneficiaryUser, double amount) {

		this.calculBalance(emailCreditUser, emailBeneficiaryUser, amount);

		try {
			this.updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(emailCreditUser, emailBeneficiaryUser);
		} catch (Exception e) {

			e.getMessage();
		}
	};

	@Override
	public void pay(String emailUser, double amount, String typeAccountUser) {
	}

	@Override
	public void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount) {
		balanceCalculatedBeneficiaryUser = 0;
		balanceCalculatedCreditUser = 0;
		double balanceBeneficiary = userAccountService.findBuddyAccountByUser(emailBeneficiaryUser).getBalance();
		double balanceCredit = userAccountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = operation.add(amount, feesTransaction);
		
		this.balanceCalculatedBeneficiaryUser = operation.add(balanceBeneficiary, amount);
		this.balanceCalculatedCreditUser = operation.withdraw(balanceCredit, amountWithFeesTransaction);
	}

	@Override
	 public   void calculBalance(String userEmail,  double amount,String typeAccountBeneficairyUser) {}
	
	
	public void updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(String emailCreditUser,
			String emailBeneficiaryUser) throws Exception {

		if (userAccountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation() == null) {
			throw new NullPointerException("Buddy Account of contact user doesn't exist");
		}

		userAccountService.updateBalanceBankingAccount(
				userAccountService.findBuddyAccountByUser(emailBeneficiaryUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBeneficiaryUser));
		userAccountService.updateBalanceBuddyAccount(userAccountService.findBuddyAccountByUser(emailCreditUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedCreditUser));
		System.out.println("balanceCalculatedBeneficiaryUser"+balanceCalculatedBeneficiaryUser);
		System.out.println("balanceCalculatedBeneficiaryUserformatted"+this.formatBalanceAccount(balanceCalculatedBeneficiaryUser));
		System.out.println("balanceCalculatedCreditUser"+balanceCalculatedCreditUser);
		System.out.println("balanceCalculatedCreditUser formatted"+this.formatBalanceAccount(balanceCalculatedCreditUser));
	}

	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}
}