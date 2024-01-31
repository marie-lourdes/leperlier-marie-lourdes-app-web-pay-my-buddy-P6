package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.domain.model.Account;

public interface IAccount {
	Account findAccountByUser(String emailUser, Account account) throws Exception ;
}
