package com.paymybuddy.webapp.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.ITransactionRepository;
import com.paymybuddy.webapp.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {
	private static final Logger log = LogManager.getLogger(TransactionService.class);
	
	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private IUserRepository userRepository;

	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public void addTransaction(long userId, String emailContact, Transaction transactionCreated)
			throws IllegalArgumentException,NullPointerException {
		UserApp creditUser = userRepository.findById(userId).get();
		// System.out.println("credit user" + creditUser);
		UserApp beneficiaryUser = userRepository.findByEmail(emailContact);
		Transaction tranferRegistered = new Transaction();

		if (beneficiaryUser == null) {
			throw new NullPointerException(" Account beneficiary  not found ");
		}else if(transactionCreated.getAmount()<=0) {
			throw new IllegalArgumentException("Amount of transaction is negative");
		} else {
			double feesTransaction = Billing.calculateFees(transactionCreated.getAmount());

			tranferRegistered.setDate(new Date());
			tranferRegistered.setCreditUser(creditUser);
			tranferRegistered.setBeneficiaryUser(beneficiaryUser);
			tranferRegistered.setDescription(transactionCreated.getDescription());
			tranferRegistered.setAmount(transactionCreated.getAmount());
			tranferRegistered.setTransactionFees(feesTransaction);
			creditUser.getTransactions().add(tranferRegistered);
		}

		transactionRepository.save(tranferRegistered);
		userRepository.save(creditUser);
	}

	public Page<Transaction> findTransactionsPaginatedByUser(String userEmail, Pageable pageable) {
		 Page<Transaction> transactionsPage=null;
		 log.debug("Retrieving  all transactions of user: {}",userEmail);
		try {
			 transactionsPage=transactionRepository.findAll(userEmail, pageable);
		} catch(Exception e) {
			log.error("Failed to retrieve alltransactions of user: {}",userEmail);
		}
		 log.debug("all transactions of user: {}retrieved successfully",userEmail);
		return transactionsPage;
	}

	public Page<Transaction> findAllTransactionsPaginated(Pageable pageable) {
		 Page<Transaction> transactionsPage=null;
		 log.debug("Retrieving  all transactions for admin");
		 try {
			 transactionsPage= transactionRepository.findAll(pageable);
		 } catch(Exception e) {
				log.error("Failed to retrieve all transactions for admin");
			}
			 log.debug("all transactions retrieved successfully for admin");
		return transactionsPage;
	}
		
}
