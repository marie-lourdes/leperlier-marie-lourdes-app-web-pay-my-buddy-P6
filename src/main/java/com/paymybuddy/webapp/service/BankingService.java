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
	@Qualifier("operationFormatImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		double userBuddyAccountBalance = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
			throw new IllegalArgumentException("balance/amount of transaction is negative");
		}
		UserDTO userContact = userAppService.getUserByEmail(emailBeneficiaryUser);
		UserDTO creditUser = userAppService.getUserByEmail(emailCreditUser);
		String userContactEmail = userContact.getEmail();
		transactionService.addTransaction(creditUser.getId(), userContactEmail, transactionCreated);
		try {
			this.updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(userContactEmail, emailCreditUser, amount);
		} catch (Exception e) {

			e.getMessage();
		}
	}

	// Mise à jour des comptes crediteur et beneficiare

	public void updateBalanceBuddyAccountsContactAndUserWithFeesTransaction(String emailBeneficiaryUser,
			String emailCreditUser, double amount) throws Exception {

		if (accountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation() == null) {
			throw new NullPointerException("Buddy Account of contact user doesn't exist");
		}

		double balanceBeneficiary = accountService.findBuddyAccountByUser(emailBeneficiaryUser).getBalance();
		double balanceCredit = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		System.out.println("balanceCredit " + balanceCredit);
		double balanceCalculatedBeneficiaryUser = addAmount(balanceBeneficiary, amount);
		double feesTransaction = Billing.calculateFees(amount);
		double amountWithFeesTransaction = addAmount(amount, feesTransaction);
		double balanceCalculatedCreditUser = withdrawAmount(balanceCredit, amountWithFeesTransaction);
		System.out.println("balanceCalculatedCreditUser1 "
				+ formatter.formatResultDecimalOperation(balanceCalculatedBeneficiaryUser));

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

			UserDTO user = userAppService.getUserByEmail(userEmail);
			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);
			updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(
					accountService.findBankingAccountByUser(userEmail).getUser().getEmail(), typeAccountBeneficiary,
					amount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Mise à jour du compte buddy de l utilisateur et compte bancaire du meme
	// utilisateur

	public void updateBalanceBankingAccountAndBuddyAccountOfUserWithFeesTransaction(String emailUser,
			String typeAccountBeneficiary, double amount) throws Exception {
		if (accountService.findBankingAccountByUser(emailUser).getCreation() == null) {
			throw new NullPointerException("Banking Account of user doesn't exist");
		}
		double balanceCalculatedBuddyAccountUser = 0;
		double balanceCalculatedBankingAccountUser = 0;
		double balanceBuddyAccount = accountService.findBuddyAccountByUser(emailUser).getBalance();
		double balanceBankingAccount = accountService.findBankingAccountByUser(emailUser).getBalance();
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

		accountService.updateBalanceAccount(accountService.findBankingAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBankingAccountUser), Constants.BANKING_ACCOUNT);
		accountService.updateBalanceAccount(accountService.findBuddyAccountByUser(emailUser).getUser().getId(),
				this.formatBalanceAccount(balanceCalculatedBuddyAccountUser), Constants.BUDDY_ACCOUNT);
	}

	public Page<Transaction> getTransactionsByUser(int pageNber, int pageSize, String email) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(pageNber - 1, pageSize, sort);
		return transactionService.findTransactionsPaginatedByUser(email, pageable);
	}

	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return operation.isOperationAuthorized(payment, userAccountBalance);
	}

	// Calcul des comptes -tranfert

	public double addAmount(double balanceCreditUser, double payment) {
		return operation.add(balanceCreditUser, payment);
	}

	public double withdrawAmount(double balanceBeneficiaryUser, double payment) {
		return operation.withdraw(balanceBeneficiaryUser, payment);
	}

	public double formatBalanceAccount(double balance) throws Exception {
		double result = formatter.formatResultDecimalOperation(balance);
		return result;
	}

}
