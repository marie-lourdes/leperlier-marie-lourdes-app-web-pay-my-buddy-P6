package com.paymybuddy.api.service.banking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IAccountRepository;
import com.paymybuddy.api.repository.IUserRepository;

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
		Account newAccount = new Account();
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
			if (account.getType().contains("Buddy Account")) {
				throw new IllegalArgumentException(
						" BuddyAccount already exist, " + "birthdate is: " + account.getCreation());
			}
		}

		newAccount.setBalance(80.0);
		newAccount.setType("Buddy Account");
		newAccount.setUser(user);
		newAccount.setCreation(new Date());

		accountRepository.save(newAccount);
	}

	public void updateBalanceBuddyAccount(String emailUserAccount, double amount) {
		Account buddyAccount = findBuddyAccountByUser(emailUserAccount);
		buddyAccount.setBalance(amount);
		accountRepository.save(buddyAccount);
	}
	
	public void updateTransactionBuddyAccount(String emailUserAccount,Transaction transaction) {
		Account buddyAccount = findBuddyAccountByUser(emailUserAccount);
		buddyAccount.getTransactions().add(transaction);
		accountRepository.save(buddyAccount);
	}

	public Account findBuddyAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account buddyAccount = new Account();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getType().contains("Buddy Account")) {
				buddyAccount.setBalance(account.getBalance());
				System.out.println("account solde " + account);
			}
		});

		return buddyAccount;
	}

	public Account findBankingAccountByUser(String emailUser) {
		Iterable<Account> allAccounts = accountRepository.findAll();
		Account bankingAccount = new Account();

		allAccounts.forEach(account -> {
			if (account.getUser().getEmail().equals(emailUser) && account.getType().contains("Banking Account")) {
				bankingAccount.setBalance(account.getBalance());
				System.out.println("account solde " + account);
			}
		});

		return bankingAccount;
	}
}
