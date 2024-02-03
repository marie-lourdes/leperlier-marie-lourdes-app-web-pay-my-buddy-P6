package com.paymybuddy.webapp.service;

public interface IBalance extends IAccount {
	void updateBalance(long id, double amount,String typeAccountBeneficiary) throws Exception;
	
}
