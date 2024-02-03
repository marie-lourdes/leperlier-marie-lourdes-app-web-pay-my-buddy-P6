package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Account;

public interface IAccount  {
	Account findAccountByUser(String emailUser, Account account) throws Exception ;
	void addBuddyAccount(String emailUser)throws Exception ; 
	
}
