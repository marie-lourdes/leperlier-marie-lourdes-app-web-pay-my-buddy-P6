package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.UserApp;

import lombok.Data;

@Data
@Component(value="accountImpl")
public class AccountImpl implements IAccount {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;
	
	private UserApp user;
	private List<Account> accountsFoundByUser ;

	@Override
	public Account findAccountByUser(String emailUser, Account userAccount) {
		 user = userRepository.findByEmail(emailUser);
		 accountsFoundByUser = accountRepository.findByUser(user);
		
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
			}
		});
		return userAccount;
	}
}
