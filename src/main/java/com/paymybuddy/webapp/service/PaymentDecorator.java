package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Transaction;

public abstract class PaymentDecorator implements IPayment{

	public abstract void pay(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) ;

}
