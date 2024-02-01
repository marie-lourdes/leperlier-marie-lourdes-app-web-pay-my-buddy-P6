package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PaymentStrategy  implements IPayment {

	@Autowired
	@Qualifier("paymentContact")
	private  PaymentContact paymentContact ;

	@Override
	public  void pay(String emailCreditUser, String emailBeneficiaryUser, double amount, String description) {
		paymentContact.pay(emailCreditUser, emailBeneficiaryUser, amount, description);
	} ;

}
