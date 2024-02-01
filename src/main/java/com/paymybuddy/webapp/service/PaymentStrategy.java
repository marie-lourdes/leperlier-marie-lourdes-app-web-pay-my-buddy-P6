package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.DTO.UserDTO;

import lombok.Data;

@Data
@Component(value="paymenStrategy")
public class PaymentStrategy {

	@Autowired
	@Qualifier("paymentContact")
	private  IPayment paymentContact ;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UserAppService userAppService;


	public  void pay(String emailCreditUser, String emailBeneficiaryUser, double balanceCredit, double balanceBeneficiary,double amount, String description) {

		paymentContact.pay(emailCreditUser, emailBeneficiaryUser,  balanceCredit, balanceBeneficiary,  amount, description);
		
	}

}
