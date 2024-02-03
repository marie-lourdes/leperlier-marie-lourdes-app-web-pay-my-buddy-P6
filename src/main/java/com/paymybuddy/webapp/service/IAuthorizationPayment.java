package com.paymybuddy.webapp.service;

public interface IAuthorizationPayment {
	public boolean isPaymentAuthorized(double payment, double userAccountBalance);
}
