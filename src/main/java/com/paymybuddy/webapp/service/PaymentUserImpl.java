package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

import lombok.Data;

@Data
@Component(value = "paymentUserImpl")
public class PaymentUserImpl implements IPayment {

	@Autowired
	@Qualifier("operationImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private AccountService accountService;

	private double balanceCalculatedBeneficiaryUser;

	private double balanceCalculatedCreditUser;

	@Override
	public void pay(String userEmail, double amount, String typeAccountBeneficairyUser) {

		this.calculBalance(userEmail, amount, typeAccountBeneficairyUser);

		try {
			this.updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(userEmail,
					typeAccountBeneficairyUser, balanceCalculatedBeneficiaryUser, balanceCalculatedCreditUser, amount);
		} catch (Exception e) {

			e.getMessage();
		}
	};

	@Override
	public void pay(String emailCreditUser, String emailBeneficiaryUser, double amount) {
	}

	@Override
	public void calculBalance(String userEmail, double amount, String typeAccountBeneficiary) {

		double balanceBuddyAccount = accountService.findBuddyAccountByUser(userEmail).getBalance();
		double balanceBankingAccount = accountService.findBankingAccountByUser(userEmail).getBalance();

		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = operation.add(amount, feesTransaction);

		if (typeAccountBeneficiary.equals(Constants.BANKING_ACCOUNT)) {
			this.balanceCalculatedBeneficiaryUser = operation.add(balanceBankingAccount, amount);
			this.balanceCalculatedCreditUser = operation.withdraw(balanceBuddyAccount, amountWithFeesTransaction);
		} else if (typeAccountBeneficiary.equals(Constants.BUDDY_ACCOUNT)) {
			this.balanceCalculatedBeneficiaryUser = operation.add(balanceBuddyAccount, amount - feesTransaction);
			// deduction des frais appliqué sur le compte beneficiare de l application et
			// non le compte bancaire qui est crediteur mais hors application
			this.balanceCalculatedCreditUser = operation.withdraw(balanceBankingAccount, amount);
		}

		System.out.println(" balanceCalculatedBeneficiaryUser" + balanceCalculatedBeneficiaryUser);
		System.out.println("   balanceCalculatedCreditUser" + balanceCalculatedCreditUser);
	}

	@Override
	public void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount) {

	}

	/*
	 * public void
	 * updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(String
	 * emailCreditUser, String emailBeneficiaryUser) throws Exception {
	 * 
	 * if (accountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation()
	 * == null) { throw new
	 * NullPointerException("Buddy Account of contact user doesn't exist"); }
	 * 
	 * accountService.updateBalanceAccount(
	 * accountService.findBuddyAccountByUser(emailBeneficiaryUser).getUser().getId()
	 * , this.formatBalanceAccount(balanceCalculatedBeneficiaryUser),
	 * Constants.BUDDY_ACCOUNT);
	 * accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(
	 * emailCreditUser).getUser().getId(),
	 * this.formatBalanceAccount(balanceCalculatedCreditUser),
	 * Constants.BUDDY_ACCOUNT); }
	 */

	public void updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String emailUser,
			String typeAccountBeneficiary, double balanceCalculatedBankingAccount,
			double balanceCalculatedBuddyAccountUser, double amount) throws Exception {
		if (accountService.findBankingAccountByUser(emailUser).getCreation() == null) {
			throw new NullPointerException("Banking Account of user doesn't exist");
		}

		accountService.updateBalanceAccount(accountService.findBankingAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBankingAccount), Constants.BANKING_ACCOUNT);
		accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBuddyAccountUser), Constants.BUDDY_ACCOUNT);
	}

	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return operation.isOperationAuthorized(payment, userAccountBalance);
	}

	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}

}
