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
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AccountService  {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	public void addBuddyAccount(String emailUser) throws IllegalArgumentException {
		UserApp user = new UserApp();
		Account newAccount = new BuddyAccount();
		List<Account> accountExisting = new ArrayList<Account>();
		try {
			user = userRepository.findByEmail(emailUser);
			accountExisting = accountRepository.findByUser(user);
			if (user == null) {
				throw new NullPointerException("user email " + emailUser + " not found");
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
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

	public void updateBalanceBuddyAccount(String emailUserAccount, double amount) {
		UserApp user = userRepository.findByEmail(emailUserAccount);
		accountRepository.updateBalanceBuddyAccount(amount, user);
	}
	
	public void updateBalanceBankingAccount(String emailUserAccount, double amount) {
		UserApp user = userRepository.findByEmail(emailUserAccount);
		accountRepository.updateBalanceBankingAccount(amount, user);
	}

	public Account findBuddyAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account buddyAccount = new BuddyAccount();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getClass() == BuddyAccount.class) {
				buddyAccount.setId(account.getId());
				buddyAccount.setCreation(account.getCreation());
				buddyAccount.setUser(account.getUser());
				buddyAccount.setBalance(account.getBalance());
				System.out.println("account solde " + buddyAccount);
			}
		});

		return buddyAccount;
	}

	public Account findBankingAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account bankingAccount = new BankingAccount();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getClass() == BankingAccount.class) {
				bankingAccount.setBalance(account.getBalance());
				System.out.println("account solde " + account);
			}
		});

		return bankingAccount;
	}
}
