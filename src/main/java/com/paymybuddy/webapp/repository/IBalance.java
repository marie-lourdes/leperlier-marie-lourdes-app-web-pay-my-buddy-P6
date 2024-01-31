package com.paymybuddy.webapp.repository;

public interface IBalance extends IAccount{
	void updateBalance(long id, double amount,String typeAccountBeneficiary) throws Exception;
}
