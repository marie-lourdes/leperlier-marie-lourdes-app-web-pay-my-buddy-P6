package com.paymybuddy.webapp;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;

public class AccountFactory {
	private AccountFactory() {

	}

	public enum AccountType {
		BANKING, BUDDY
	};

	public static Account makeAccount(AccountType type) {
		switch (type) {
		case BANKING:
			return new BankingAccount();
		case BUDDY:
			return new BuddyAccount();
		}
		return new BuddyAccount();
	}
}
