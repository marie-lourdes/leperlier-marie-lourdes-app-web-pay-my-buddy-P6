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
	private double balanceBankingAccountCalculated;
	private double balanceBuddyAccountCalculated;

	@Override
	public void pay(String userEmail, double amount, String typeAccountBeneficairyUser) {

		try {
			this.calculBalance(userEmail, amount, typeAccountBeneficairyUser);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void pay(String emailCreditUser, String emailBeneficiaryUser, double amount) {
	}

	@Override
	public void calculBalance(String userEmail, double amount, String typeAccountBeneficiaryUser) throws Exception {

		double balanceBuddyAccount = accountService.findBuddyAccountByUser(userEmail).getBalance();
		double balanceBankingAccount = accountService.findBankingAccountByUser(userEmail).getBalance();
		balanceBankingAccountCalculated = 0;
		balanceBuddyAccountCalculated = 0;

		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = operation.add(amount, feesTransaction);
		if (typeAccountBeneficiaryUser.equals(Constants.BANKING_ACCOUNT)) {
			balanceBankingAccountCalculated = operation.add(balanceBankingAccount, amount);
			balanceBuddyAccountCalculated = operation.withdraw(balanceBuddyAccount, amountWithFeesTransaction);

			System.out.println("balanceBankingAccountCalculated" + balanceBankingAccountCalculated);
			System.out.println("balanceBankingAccountCalculated rformatted"
					+ this.formatBalanceAccount(balanceBankingAccountCalculated));
			System.out.println("balanceBuddyAccount" + balanceBuddyAccountCalculated);
			System.out.println("balanceBuddyAccountCalculated formatted"
					+ this.formatBalanceAccount(balanceBuddyAccountCalculated));
		}
		if (typeAccountBeneficiaryUser.equals(Constants.BUDDY_ACCOUNT)) {
			balanceBuddyAccountCalculated = operation.add(balanceBuddyAccount, amount - feesTransaction);
			// deduction des frais appliqué sur le compte beneficiare de l application et
			// non le compte bancaire qui est crediteur mais hors application
			balanceBankingAccountCalculated = operation.withdraw(balanceBankingAccount, amount);

			System.out.println("balanceBankingAccountCalculated" + balanceBankingAccountCalculated);
			System.out.println("balanceBankingAccountCalculated rformatted"
					+ this.formatBalanceAccount(balanceBankingAccountCalculated));
			System.out.println("balanceBuddyAccount" + balanceBuddyAccountCalculated);
			System.out.println("balanceBuddyAccountCalculated formatted"
					+ this.formatBalanceAccount(balanceBuddyAccountCalculated));
		}
		this.updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(userEmail,
				this.formatBalanceAccount(balanceBankingAccountCalculated),
				this.formatBalanceAccount(balanceBuddyAccountCalculated));
		System.out.println("balanceBankingAccountCalculated" + balanceBankingAccountCalculated);
	}

	@Override
	public void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount) throws Exception {

	}

	public void updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String emailUser,
			double balanceCalculatedBankingAccount, double balanceCalculatedBuddyAccountUser) throws Exception {

		if (accountService.findBankingAccountByUser(emailUser).getCreation() == null) {
			throw new NullPointerException("Banking Account of user doesn't exist");
		}
		if (accountService.findBuddyAccountByUser(emailUser).getCreation() == null) {
			throw new NullPointerException("Buddy Account of user doesn't exist");
		}

		accountService.updateBalanceBankingAccount(accountService.findBankingAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBankingAccount));
		accountService.updateBalanceBuddyAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBuddyAccountUser));
	}

	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}

}