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
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		try {
			double userBuddyAccountCreditUserBalance = accountService.findBuddyAccountByUser(emailCreditUser)
					.getBalance();
			Date userBuddyAccountBeneficiaryUser = accountService.findBuddyAccountByUser(emailCreditUser).getCreation();
			if (userBuddyAccountBeneficiaryUser == null) {
				throw new Exception("Buddy Account not found");
			} else if (isPaymentAuthorized(amount, userBuddyAccountCreditUserBalance)) {
				throw new Exception("balance Buddy Account/amount of transaction is negative");
			}

			payment.pay(emailCreditUser, emailBeneficiaryUser, amount);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void transferMoneyToBankingAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		try {
		
			double userBuddyAccountBalance = accountService.findBuddyAccountByUser(userEmail).getBalance();
			Date userBankingAccount = accountService.findBankingAccountByUser(userEmail).getCreation();
			if (userBankingAccount == null) {
				throw new Exception("Banking Account not found");
			} else if (isPaymentAuthorized(amount, userBuddyAccountBalance)) {
				throw new Exception("balance Buddy Account/amount of transaction is negative");
			}

			payment.pay(userEmail, amount, Constants.BANKING_ACCOUNT);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void transferMoneyToBuddyAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {

		try {
			double userBankingAccountBalance = accountService.findBankingAccountByUser(userEmail).getBalance();
			Date userBuddyAccount = accountService.findBankingAccountByUser(userEmail).getCreation();
			if (userBuddyAccount == null) {
				throw new Exception("Buddy Account not found");
			} else if (isPaymentAuthorized(amount, userBankingAccountBalance)) {
				throw new Exception("balance Banking Account/amount of transaction is negative");
			}
			UserDTO user = userAppService.getUserByEmail(userEmail);
			payment.pay(userEmail, amount, Constants.BUDDY_ACCOUNT);

			transactionService.addTransaction(user.getId(), user.getEmail(), transactionCreated);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
