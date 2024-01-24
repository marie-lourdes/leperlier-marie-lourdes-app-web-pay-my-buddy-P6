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
public class AccountService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	public void addBuddyAccount(String emailUser) throws IllegalArgumentException {
		UserApp user = new UserApp();
		BuddyAccount newAccount = new BuddyAccount();
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

	public void updateBalanceBuddyAccount(long id, double amount) {
		UserApp user = userRepository.findById(id).get();
		accountRepository.updateBalanceBuddyAccount(amount, user);
	}

	public void updateBalanceBankingAccount(long id, double amount) 	throws  NullPointerException{
		UserApp user = new UserApp();
		try {
			user = userRepository.findById(id).get();
		}catch(NullPointerException e) {
			throw new NullPointerException("this account doesn't exist");
		}
		
		accountRepository.updateBalanceBankingAccount(amount, user);
	}

	public BuddyAccount findBuddyAccountByUser(String emailUser) {
		List<Account> allAccounts = accountRepository.findAll();
		BuddyAccount buddyAccount = new BuddyAccount();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getClass() == BuddyAccount.class) {
				buddyAccount.setId(account.getId());
				buddyAccount.setCreation(account.getCreation());
				buddyAccount.setUser(account.getUser());
				buddyAccount.setBalance(account.getBalance());

			}
		});
		// System.out.println(buddyAccount);
		return buddyAccount;
	}

	public BankingAccount findBankingAccountByUser(String emailUser) {
		List<Account> allAccounts = accountRepository.findAll();
		BankingAccount bankingAccount = new BankingAccount();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getClass() == BankingAccount.class) {
				bankingAccount.setId(account.getId());
				bankingAccount.setCreation(account.getCreation());
				bankingAccount.setUser(account.getUser());
				bankingAccount.setBalance(account.getBalance());
				// bankingAccount.setTransactions(account.getTransactions());

			}
		});
		// System.out.println(bankingAccount);
		return bankingAccount;
	}
}
