package com.paymybuddy.webapp.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.Transaction;
@Component
public class TransactionMapper {
	public TransactionDTO TransactionToTransactionDTO(Transaction transaction) {
		String contactName = transaction.getBeneficiaryUser().getFirstName() + transaction.getBeneficiaryUser().getLastName();
	    String description = transaction.getDescription();	
	    double amount = transaction.getAmount();		
	     
		return new TransactionDTO (contactName , description,amount);
	}
}


