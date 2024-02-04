package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Account;

public interface IBalance extends IAccount {
	void updateBalance(long id, double amount, Account account) throws Exception;
	
}