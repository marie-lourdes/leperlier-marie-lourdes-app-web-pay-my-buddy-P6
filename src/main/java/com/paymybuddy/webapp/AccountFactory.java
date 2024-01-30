package com.paymybuddy.webapp;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;

public class AccountFactory {
	private AccountFactory() {

	}

	public enum AccountType {
		BANKING_ACCOUNT, BUDDY_ACCOUNT
	};

	public static Account makeAccount(AccountType type) {
		switch (type) {
		case BANKING_ACCOUNT:
			return new BankingAccount();
		case BUDDY_ACCOUNT:
			return new BuddyAccount();
		}
		return new BuddyAccount();
	}
}
