package com.paymybuddy.webapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.IFormat;

@Transactional
@Service
public class PaymentService implements IAuthorizationPayment {

	@Autowired
	@Qualifier("operationImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private PaymentStrategy payment;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		Date userBuddyAccountBeneficiaryUser = null;
		double userBuddyAccountCreditUserBalance = 0;

		userBuddyAccountCreditUserBalance = accountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		userBuddyAccountBeneficiaryUser = accountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation();
		System.out.println("userBuddyAccountBeneficiaryUser" + userBuddyAccountBeneficiaryUser);
		if (userBuddyAccountBeneficiaryUser == null) {
			throw new NullPointerException("Buddy Account not found");
		} else if (isPaymentAuthorized(amount, userBuddyAccountCreditUserBalance)) {
			throw new IllegalArgumentException("balance Buddy Account/amount of transaction is negative");
		}
		payment.pay(emailCreditUser, emailBeneficiaryUser, amount);

	}

	public void transferMoneyToBankingAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		double userCreditBuddyAccountBalance = accountService.findBuddyAccountByUser(userEmail).getBalance();
		Date userBankingAccount = accountService.findBankingAccountByUser(userEmail).getCreation();
		if (userBankingAccount == null) {
			throw new NullPointerException("Banking Account not found");
		} else if (isPaymentAuthorized(amount, userCreditBuddyAccountBalance)) {
			throw new IllegalArgumentException("balance Buddy Account/amount of transaction is negative");
		}

		payment.pay(userEmail, amount, Constants.BANKING_ACCOUNT);

	}

	public void transferMoneyToBuddyAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		double userCreditBankingAccountBalance = accountService.findBankingAccountByUser(userEmail).getBalance();
		Date userBuddyAccount = accountService.findBuddyAccountByUser(userEmail).getCreation();
		if (userBuddyAccount == null) {
			throw new NullPointerException("Buddy Account not found");
		} else if (isPaymentAuthorized(amount, userCreditBankingAccountBalance)) {
			throw new IllegalArgumentException("balance Banking Account/amount of transaction is negative");
		}

		payment.pay(userEmail, amount, Constants.BUDDY_ACCOUNT);
	}

	public Page<Transaction> getTransactionsByUser(int pageNber, int pageSize, String email) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(pageNber - 1, pageSize, sort);
		return transactionService.findTransactionsPaginatedByUser(email, pageable);
	}

	@Override
	public boolean isPaymentAuthorized(double payment, double userAccountBalance) {
		return operation.isOperationAuthorized(payment, userAccountBalance);
	}

}
