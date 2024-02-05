package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.AccountFactory;
import com.paymybuddy.webapp.AccountFactory.AccountType;
import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.ConstantsException;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AccountService {

	@Autowired
	@Qualifier("accountImpl")
	private IBalance account;

	public void addBuddyAccount(String emailUser) throws Exception {
		
			BuddyAccount existingBuddyAccount  = this.findBuddyAccountByUser(emailUser);

			if (existingBuddyAccount != null) {
				if (existingBuddyAccount.getUser().getEmail().equals(emailUser)) {
					throw new IllegalArgumentException("Buddy Account already exist!");
				}
			}
			account.addBuddyAccount(emailUser);
		
	}

	public void updateBalanceBuddyAccount(long id, double amount)
			throws NullPointerException {

		try {
			account.updateBalance(id, amount, AccountFactory.makeAccount(AccountType.BUDDY));
		} catch (NullPointerException e) {
			throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void updateBalanceBankingAccount(long id, double amount)
			throws NullPointerException {

		try {
			account.updateBalance(id, amount, AccountFactory.makeAccount(AccountType.BANKING));
		} catch (NullPointerException e) {
			throw new NullPointerException(ConstantsException.BANKING_ACCOUNT_NULL_EXCEPTION);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public BuddyAccount findBuddyAccountByUser(String emailUser) throws NullPointerException {
		BuddyAccount userBuddyAccount = null;

		try {
			userBuddyAccount = (BuddyAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BUDDY));
			if (userBuddyAccount == null) {

				throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION+" for " + emailUser);
			}
		} catch (Exception e) {
			e.getMessage();

		}

		return userBuddyAccount;
	}

	public BankingAccount findBankingAccountByUser(String emailUser) throws NullPointerException {
		BankingAccount userBankingAccount = null;

		try {
			userBankingAccount = (BankingAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BANKING));
			if (userBankingAccount == null) {

				throw new NullPointerException(ConstantsException.BANKING_ACCOUNT_NULL_EXCEPTION+" for " + emailUser);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return userBankingAccount;
	}

}