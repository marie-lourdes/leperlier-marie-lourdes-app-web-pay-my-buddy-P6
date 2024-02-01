package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.utils.IFormat;

import lombok.Data;

@Data
@Component(value="paymenStrategy")
public class PaymentStrategy {

	@Autowired
	@Qualifier("paymentContact")
	private  IPayment paymentContact ;
	
	@Autowired
	private AccountService accountService;


	public  void pay(String emailCreditUser, String emailBeneficiaryUser,double amount, String description) {

		double balanceBeneficiary = accountService.findBuddyAccountByUser(emailBeneficiaryUser).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		paymentContact.pay(emailCreditUser, emailBeneficiaryUser,  balanceCredit, balanceBeneficiary,  amount, description);
	} ;

}
