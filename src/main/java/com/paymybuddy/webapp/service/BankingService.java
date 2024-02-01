package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

@Service
public class BankingService {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		
		UserDTO userContact = userAppService.getUserByEmail(emailBeneficiaryUser);
		UserDTO creditUser = userAppService.getUserByEmail(emailCreditUser);
		
		
		

		transactionService.addTransaction(creditUser.getId(),emailCreditUser, transactionCreated);
		try {
			this.updateBalanceBuddyAccountsContactAndUserWithFeesTransaction( emailCreditUser,emailBeneficiaryUser,balanceCalculatedCreditUser,balanceCalculatedBeneficiaryUser, amount);
		} catch (Exception e) {

			e.getMessage();
		}
	}

	// Mise à jour des comptes crediteur et beneficiare

	public void updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(String emailCreditUser,String emailBeneficiaryUser,
				double balanceCalculatedCreditUser,	double balanceCalculatedBeneficiaryUser, double amount) throws Exception {

		if (accountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation() == null) {
			throw new NullPointerException("Buddy Account of contact user doesn't exist");
		}
	
		accountService.updateBalanceAccount(
				accountService.findBuddyAccountByUser(emailBeneficiaryUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBeneficiaryUser), Constants.BUDDY_ACCOUNT);
		accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(emailCreditUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedCreditUser), Constants.BUDDY_ACCOUNT);
	}

	
	public void transferMoneyToBankingAccountUser(String userEmail, String typeAccountBeneficiary, double amount,
			String description, Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		try {
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userEmail).getBalance();
			if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}


			double balanceCalculatedBuddyAccountUser = 0;
			double balanceCalculatedBankingAccountUser = 0;
			double balanceBuddyAccount = accountService.findBuddyAccountByUser(userEmail).getBalance();
			double balanceBankingAccount = accountService.findBankingAccountByUser(userEmail).getBalance();
			double feesTransaction = Billing.calculateFees(amount);

			double amountWithFeesTransaction = addAmount(amount, feesTransaction);

			if (typeAccountBeneficiary.equals(Constants.BANKING_ACCOUNT)) {
				balanceCalculatedBankingAccountUser = addAmount(balanceBankingAccount, amount);
				balanceCalculatedBuddyAccountUser = withdrawAmount(balanceBuddyAccount, amountWithFeesTransaction);
			} else if (typeAccountBeneficiary.equals(Constants.BUDDY_ACCOUNT)) {
				balanceCalculatedBuddyAccountUser = addAmount(balanceBuddyAccount, amount - feesTransaction);
				// deduction des frais appliqué sur le compte beneficiare de l application et
				// non le compte bancaire qui est crediteur mais hors application
				balanceCalculatedBankingAccountUser = withdrawAmount(balanceBankingAccount, amount);
			}
			
			UserDTO user = userAppService.getUserByEmail(userEmail);
			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);
			this.updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(
					accountService.findBuddyAccountByUser(userEmail).getUser().getEmail(), typeAccountBeneficiary,
					amount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// !!!regrouper les deux méthodes qui ont quasiment la meme implementation sauf
	// l appel de la method acourndService.findbuddyaccount, et findBankingAccount

	public void transferMoneyToBuddyAccountUser(String userEmail, String typeAccountBeneficiary, double amount,
			String description, Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		try {
			double userBankingAccountBalance = accountService.findBankingAccountByUser(userEmail).getBalance();
			if (isPaymentAuthorized(amount, userBankingAccountBalance)) {
				throw new Exception("balance/amount of transaction is negative");
			}

			double balanceCalculatedBuddyAccountUser = 0;
			double balanceCalculatedBankingAccountUser = 0;
			double balanceBuddyAccount = accountService.findBuddyAccountByUser(userEmail).getBalance();
			double balanceBankingAccount = accountService.findBankingAccountByUser(userEmail).getBalance();
			double feesTransaction = Billing.calculateFees(amount);

			double amountWithFeesTransaction = addAmount(amount, feesTransaction);

			if (typeAccountBeneficiary.equals(Constants.BANKING_ACCOUNT)) {
				balanceCalculatedBankingAccountUser = addAmount(balanceBankingAccount, amount);
				balanceCalculatedBuddyAccountUser = withdrawAmount(balanceBuddyAccount, amountWithFeesTransaction);
			} else if (typeAccountBeneficiary.equals(Constants.BUDDY_ACCOUNT)) {
				balanceCalculatedBuddyAccountUser = addAmount(balanceBuddyAccount, amount - feesTransaction);
				// deduction des frais appliqué sur le compte beneficiare de l application et
				// non le compte bancaire qui est crediteur mais hors application
				balanceCalculatedBankingAccountUser = withdrawAmount(balanceBankingAccount, amount);
			}
			
			UserDTO user = userAppService.getUserByEmail(userEmail);
			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);
			
			this.updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(
					accountService.findBankingAccountByUser(userEmail).getUser().getEmail(), typeAccountBeneficiary, balanceCalculatedBankingAccountUser, balanceCalculatedBuddyAccountUser,
					amount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour du compte buddy de l utilisateur et compte bancaire du meme
	// utilisateur

	public void updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String emailUser,
			String typeAccountBeneficiary, double balanceBankingAccount,	double balanceCalculatedBuddyAccountUser , double amount) throws Exception {
		if (accountService.findBankingAccountByUser(emailUser).getCreation() == null) {
			throw new NullPointerException("Banking Account of user doesn't exist");
		}
	
		accountService.updateBalanceAccount(accountService.findBankingAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBuddyAccountUser), Constants.BANKING_ACCOUNT);
		accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBuddyAccountUser), Constants.BUDDY_ACCOUNT);
	}

	public Page<Transaction> getTransactionsByUser(int pageNber, int pageSize, String email) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(pageNber - 1, pageSize, sort);
		return transactionService.findTransactionsPaginatedByUser(email, pageable);
	}



}
