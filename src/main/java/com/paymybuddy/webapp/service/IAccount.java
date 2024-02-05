package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BuddyAccount;

public interface IAccount  {
	Account findAccountByUser(String emailUser, Account account) throws Exception ;
	BuddyAccount addBuddyAccount(String emailUser)throws Exception ; 
	
}
