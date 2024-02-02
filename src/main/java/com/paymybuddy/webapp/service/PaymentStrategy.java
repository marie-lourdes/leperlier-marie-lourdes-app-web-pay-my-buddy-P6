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
	@Qualifier("paymentContactImpl")
	private  IPayment paymentContact ;
	
	@Autowired
	@Qualifier("paymentUserImpl")
	private  IPayment paymentUser ;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UserAppService userAppService;


	public  void pay(String emailCreditUser, String emailBeneficiaryUser,double amount) {

		paymentContact.pay(emailCreditUser, emailBeneficiaryUser,  amount );
		
	}
	
	public  void pay(String userEmail, double amount,String typeAccountUser) {

		paymentUser.pay(userEmail,  amount, typeAccountUser);
		
	}

}
