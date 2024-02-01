package com.paymybuddy.webapp.repository;

public interface IBalance {
	void updateBalance(long id, double amount,String typeAccountBeneficiary) throws Exception;
}
