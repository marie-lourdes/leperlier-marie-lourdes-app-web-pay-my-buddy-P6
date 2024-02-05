package com.paymybuddy.webapp.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.utils.Constants;
import com.paymybuddy.webapp.utils.ConstantsException;
import com.paymybuddy.webapp.utils.IFormat;

@Transactional
@Service
public class PaymentService implements IAuthorizationPayment {
	private static final Logger log = LogManager.getLogger(PaymentService.class);
	
	@Autowired
	@Qualifier("operationImpl")
	private IOperation operation;

	@Autowired
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@Autowired
	private PaymentStrategy payment;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionService transactionService;

	public void payToContact(String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		log.debug("Payment in progress with contact: {}  " ,emailBeneficiaryUser);
		Date userBuddyAccountBeneficiaryUser = null;
		double userBuddyAccountCreditUserBalance = 0;

		userBuddyAccountCreditUserBalance = userAccountService.findBuddyAccountByUser(emailCreditUser).getBalance();
		userBuddyAccountBeneficiaryUser = userAccountService.findBuddyAccountByUser(emailBeneficiaryUser).getCreation();
		System.out.println("userBuddyAccountBeneficiaryUser" + userBuddyAccountBeneficiaryUser);
		if (userBuddyAccountBeneficiaryUser == null) {
		
			throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION+ ": " +emailBeneficiaryUser);
		} else if (isPaymentAuthorized(amount, userBuddyAccountCreditUserBalance)) {
			throw new IllegalArgumentException(ConstantsException.PAYMENT_ARGUMENT_EXCEPTION);
		}
		
		payment.pay(emailCreditUser, emailBeneficiaryUser, amount);
		log.debug("Payment on contact : {}  made successfully" ,emailBeneficiaryUser);
	}

	public void transferMoneyToBankingAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		log.debug("Payment in progress with BankingAccount of User {}  " ,userEmail);
		double userCreditBuddyAccountBalance = userAccountService.findBuddyAccountByUser(userEmail).getBalance();
		Date userBankingAccount = userAccountService.findBankingAccountByUser(userEmail).getCreation();
		if (userBankingAccount == null) {
			throw new NullPointerException(ConstantsException.BANKING_ACCOUNT_NULL_EXCEPTION+": " + userEmail);
		} else if (isPaymentAuthorized(amount, userCreditBuddyAccountBalance)) {
			throw new IllegalArgumentException(ConstantsException.PAYMENT_ARGUMENT_EXCEPTION);
		}

		payment.pay(userEmail, amount, Constants.BANKING_ACCOUNT);
		log.debug("Payment on BankingAccount of User: {} made successfully{}  " , userEmail);
	}

	public void transferMoneyToBuddyAccountUser(String userEmail, double amount, String description,
			Transaction transactionCreated) throws IllegalArgumentException, NullPointerException {
		log.debug("Payment in progress with BuddyAccount of User {}  " ,userEmail);
		double userCreditBankingAccountBalance = userAccountService.findBankingAccountByUser(userEmail).getBalance();
		Date userBuddyAccount = userAccountService.findBuddyAccountByUser(userEmail).getCreation();
		if (userBuddyAccount == null) {
			throw new NullPointerException(ConstantsException.BUDDY_ACCOUNT_NULL_EXCEPTION +": " + userEmail);
		} else if (isPaymentAuthorized(amount, userCreditBankingAccountBalance)) {
			throw new IllegalArgumentException(ConstantsException.PAYMENT_ARGUMENT_EXCEPTION);
		}

		payment.pay(userEmail, amount, Constants.BUDDY_ACCOUNT);
		log.debug("Payment on BuddyAccount of User: {} made successfully{}  " , userEmail);
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
