package com.paymybuddy.webapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.AccountFactory;
import com.paymybuddy.webapp.AccountFactory.AccountType;
import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IAccount;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.IBalance;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.utils.Constants;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AccountService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	@Qualifier("accountImpl")
	private IBalance account;

	public void addBuddyAccount(String emailUser) throws IllegalArgumentException, NullPointerException {
		UserApp user = new UserApp();
		BuddyAccount newAccount = new BuddyAccount();
		List<Account> accountExisting = new ArrayList<Account>();

		user = userRepository.findByEmail(emailUser);
		accountExisting = accountRepository.findByUser(user);
		if (user == null) {
			throw new NullPointerException("user " + emailUser + " not found");
		}

		for (Account account : accountExisting) {
			if (account.getUser().getEmail().equals(emailUser)) {
				throw new IllegalArgumentException(
						" BuddyAccount already exist, " + "birthdate is: " + account.getCreation());
			}
		}
		newAccount.setBalance(80.0);
		newAccount.setUser(user);
		newAccount.setCreation(new Date());

		accountRepository.save(newAccount);
	}

	public void updateBalanceBuddyAccount(long id, double amount) throws NullPointerException {
		UserApp user = new UserApp();

		try {
			user = userRepository.findById(id).get();
			accountRepository.updateBalanceBuddyAccount(amount, user);
		} catch (NullPointerException e) {
			throw new NullPointerException("this buddy account doesn't exist");
		}
	}

	public void updateBalanceAccount(long id, double amount, String typeAccountBeneficiary)
			throws NullPointerException {

		try {
			account.updateBalance(id, amount, typeAccountBeneficiary);
		} catch (NullPointerException e) {
			throw new NullPointerException("this banking account doesn't exist");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public BuddyAccount findBuddyAccountByUser(String emailUser) throws NullPointerException {
		BuddyAccount userBuddyAccount = null;

		try {
			userBuddyAccount = (BuddyAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BUDDY));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userBuddyAccount;
	}

	public BankingAccount findBankingAccountByUser(String emailUser) throws NullPointerException {
		BankingAccount userBankingAccount = null;

		try {
			userBankingAccount = (BankingAccount) account.findAccountByUser(emailUser,
					AccountFactory.makeAccount(AccountType.BANKING));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBankingAccount;
	}

	/*
	 * @Override public Account findAccountByUser(String emailUser, Account
	 * userAccount) { UserApp user = userRepository.findByEmail(emailUser);
	 * List<Account> accountsFoundByUser = accountRepository.findByUser(user);
	 * //BankingAccount bankingAccount = new BankingAccount();
	 * 
	 * if (user == null) { throw new NullPointerException("user " + emailUser +
	 * " not found"); } else if (accountsFoundByUser.isEmpty()) { throw new
	 * NullPointerException("account not found"); }
	 * 
	 * accountsFoundByUser.forEach(account -> { if (account.getClass() ==
	 * BankingAccount.class) { userAccount.setId(account.getId());
	 * userAccount.setCreation(account.getCreation());
	 * userAccount.setUser(account.getUser());
	 * userAccount.setBalance(account.getBalance()); //
	 * bankingAccount.setTransactions(account.getTransactions()); } }); //
	 * System.out.println(bankingAccount); return userAccount; }
	 */
}
