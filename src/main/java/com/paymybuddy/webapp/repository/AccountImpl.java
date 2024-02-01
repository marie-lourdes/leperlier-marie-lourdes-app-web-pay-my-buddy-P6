package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.Billing;
import com.paymybuddy.webapp.service.IOperation;
import com.paymybuddy.webapp.utils.Constants;

import lombok.Data;

@Data
@Component(value = "accountImpl")
public class AccountImpl implements IBalance {

	@Autowired
	@Qualifier("operationFormatImpl")
	private IOperation operation;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	private UserApp user;
	private List<Account> accountsFoundByUser;
	

	@Override
	public Account findAccountByUser(String emailUser, Account userAccount)  throws Exception{
		user = userRepository.findByEmail(emailUser);
		accountsFoundByUser = accountRepository.findByUser(user);

		if (user == null) {
			throw new NullPointerException("user " + emailUser + " not found");
		} else if (accountsFoundByUser.isEmpty()) {
			throw new NullPointerException("account not found");
		}

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
	public void updateBalance(long id, double amount, String typeAccountBeneficiary) throws Exception {
		 user = new UserApp();
		user = userRepository.findById(id).get();
		if (typeAccountBeneficiary.equals(Constants.BUDDY_ACCOUNT)) {
			
			accountRepository.updateBalanceBuddyAccount(amount, user);
		} else if (typeAccountBeneficiary.equals(Constants.BANKING_ACCOUNT)) {
			
			accountRepository.updateBalanceBankingAccount(amount, user);
		}
	}
	
}
