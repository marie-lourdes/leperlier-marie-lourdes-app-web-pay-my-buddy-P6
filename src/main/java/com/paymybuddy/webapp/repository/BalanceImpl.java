package com.paymybuddy.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.utils.Constants;

public class BalanceImpl implements IBalance{
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;
	
	@Override
	public void updateBalance(long id, double amount, String typeAccountBeneficiary) throws Exception {
		UserApp user = new UserApp();
		user = userRepository.findById(id).get();
		if (typeAccountBeneficiary.equals(Constants.BUDDY_ACCOUNT)) {
			
			accountRepository.updateBalanceBuddyAccount(amount, user);
		} else if (typeAccountBeneficiary.equals(Constants.BANKING_ACCOUNT)) {
			
			accountRepository.updateBalanceBankingAccount(amount, user);
		}
	}
}
