package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.utils.IFormat;

import lombok.Data;

@Data
@Component(value="paymentContact")
public class PaymentContact implements IPayment {
	
	@Autowired
	@Qualifier("operationFormatImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;
	
	@Autowired
	private AccountService accountService;

	
	 @Override
	public void pay(String emailCreditUser, String emailBeneficiaryUser, double amount, String description) {

			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
			if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
				throw new IllegalArgumentException("balance/amount of transaction is negative");
			}
			
			double balanceBeneficiary = accountService.findBuddyAccountByUser(emailBeneficiaryUser).getBalance();
			double balanceCredit = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
			System.out.println("balanceCredit " + balanceCredit);
			double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
			double feesTransaction = Billing.calculateFees(amount);
			double amountWithFeesTransaction = addAmount(amount, feesTransaction);
			double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
		
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
