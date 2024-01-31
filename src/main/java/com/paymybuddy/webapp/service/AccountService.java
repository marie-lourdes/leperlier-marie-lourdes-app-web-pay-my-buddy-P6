package com.paymybuddy.webapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IAccount;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AccountService implements IAccount {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

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

	public void updateBalanceBuddyAccount(long id, double amount) throws NullPointerException{
		UserApp user = new UserApp();
		
		try {
			user = userRepository.findById(id).get();
			accountRepository.updateBalanceBuddyAccount(amount, user);
		} catch (NullPointerException e) {
			throw new NullPointerException("this buddy account doesn't exist");
		}
	}

	public void updateBalanceBankingAccount(long id, double amount) throws NullPointerException {
		UserApp user = new UserApp();
		
		try {
			user = userRepository.findById(id).get();
			accountRepository.updateBalanceBankingAccount(amount, user);

		} catch (NullPointerException e) {
			throw new NullPointerException("this banking account doesn't exist");
		}
	}

	public BuddyAccount findBuddyAccountByUser(String emailUser) throws NullPointerException {
		return  (BuddyAccount) findAccountByUser(emailUser, new BuddyAccount());
	}

	public BankingAccount findBankingAccountByUser(String emailUser) throws NullPointerException {
		 
		return  (BankingAccount) findAccountByUser(emailUser, new BankingAccount());
	}

	@Override
	public Account findAccountByUser(String emailUser, Account userAccount) {
		UserApp user = userRepository.findByEmail(emailUser);
		List<Account> accountsFoundByUser = accountRepository.findByUser(user);
		//BankingAccount bankingAccount = new BankingAccount();

		if (user == null) {
			throw new NullPointerException("user " + emailUser + " not found");
		} else if (accountsFoundByUser.isEmpty()) {
			throw new NullPointerException("account not found");
		}

		accountsFoundByUser.forEach(account -> {
			if (account.getClass() == BankingAccount.class) {
				userAccount.setId(account.getId());
				userAccount.setCreation(account.getCreation());
				userAccount.setUser(account.getUser());
				userAccount.setBalance(account.getBalance());
				// bankingAccount.setTransactions(account.getTransactions());
			}
		});
// System.out.println(bankingAccount);
		return userAccount;
	}
}
