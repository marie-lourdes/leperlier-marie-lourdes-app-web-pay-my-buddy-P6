package com.paymybuddy.webapp.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.Transaction;

@Component
public class TransactionMapper {

	public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
		String contactName = transaction.getBeneficiaryUser().getFirstName() + " "
				+ transaction.getBeneficiaryUser().getLastName();
		String description = transaction.getDescription();
		long amount = transaction.getAmount();

		return new TransactionDTO(contactName, description, amount);
	}

	public TransactionBillingDTO transactionToTransactionBillingDTO(Transaction transaction) {
		String userCreditName = transaction.getCreditUser().getFirstName();
		String contactName = transaction.getBeneficiaryUser().getFirstName() + " "
				+ transaction.getBeneficiaryUser().getLastName();
		String description = transaction.getDescription();
		long amount = transaction.getAmount();
		double fees = transaction.getTransactionFees();

		return new TransactionBillingDTO(userCreditName, contactName, description, amount, fees);
	}
}
