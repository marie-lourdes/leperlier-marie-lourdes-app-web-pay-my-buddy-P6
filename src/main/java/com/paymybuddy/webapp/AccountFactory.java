package com.paymybuddy.webapp;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;

public class AccountFactory {
	private AccountFactory() {

	}

	public enum AccountType {
		BANKING, BUDDY
	}

	public static Account makeAccount(AccountType type) {
		if(type==AccountType.BANKING) {
			return new BankingAccount();
		}
			return new BuddyAccount();			
	}
}
