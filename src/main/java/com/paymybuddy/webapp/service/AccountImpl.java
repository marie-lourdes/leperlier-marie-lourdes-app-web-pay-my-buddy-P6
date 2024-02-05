package com.paymybuddy.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.AccountFactory;
import com.paymybuddy.webapp.AccountFactory.AccountType;
import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.IAccountRepository;
import com.paymybuddy.webapp.repository.IUserRepository;

import lombok.Data;

@Data
@Component(value = "accountImpl")
public class AccountImpl implements IBalance {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	private List<Account> accountsFoundByUser;

	@Override
	public BuddyAccount addBuddyAccount(String emailUser) throws Exception {
		UserApp user = new UserApp();
		user = userRepository.findByEmail(emailUser);
		BuddyAccount newAccount = (BuddyAccount) AccountFactory.makeAccount(AccountType.BUDDY);

		BuddyAccount existingBuddyAccount = (BuddyAccount) this.findAccountByUser(emailUser, new BuddyAccount());
		if (existingBuddyAccount != null) {
			if (existingBuddyAccount.getUser().getEmail().equals(emailUser)) {
				throw new IllegalArgumentException("Buddy Account already exist!");
			}
		}

		newAccount.setBalance(80.0);
		newAccount.setUser(user);
		newAccount.setCreation(new Date());

		accountRepository.save(newAccount);
		return newAccount;
	}

	@Override
	public Account findAccountByUser(String emailUser, Account userAccount) throws Exception {
		UserApp user = new UserApp();
		user = userRepository.findByEmail(emailUser);
		accountsFoundByUser = accountRepository.findByUser(user);
		accountsFoundByUser.forEach(account -> {
			if (account.getClass() == userAccount.getClass()) {
				userAccount.setId(account.getId());
				userAccount.setCreation(account.getCreation());
				userAccount.setUser(account.getUser());
				userAccount.setBalance(account.getBalance());
			}
		});
		return userAccount;
	}

	@Override
	public void updateBalance(long id, double amount, Account account) throws Exception {
		UserApp user = new UserApp();
		user = userRepository.findById(id).get();

		if (account.getClass() == BuddyAccount.class) {
			accountRepository.updateBalanceBuddyAccount(amount, user);
		} else if (account.getClass() == BankingAccount.class) {
			accountRepository.updateBalanceBankingAccount(amount, user);
		}
	}
}