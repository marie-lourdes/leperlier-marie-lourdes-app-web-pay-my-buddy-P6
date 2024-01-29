package com.paymybuddy.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.repository.ITransactionRepository;
import com.paymybuddy.webapp.repository.IUserRepository;
import com.paymybuddy.webapp.utils.Billing;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private IUserRepository userRepository;

	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	/*public List<Transaction> getTransactionsByCreditUser(UserApp creditUser) {
		return transactionRepository.findByCreditUser(creditUser);
	}*/

	public void addTransaction(long userId, String contactId, Transaction transactionCreated)
			throws IllegalArgumentException {
		UserApp creditUser = userRepository.findById(userId).get();
		// System.out.println("credit user" + creditUser);
		UserApp beneficiaryUser = userRepository.findByEmail(contactId);
		Transaction tranferRegistered = new Transaction();

		if (beneficiaryUser == null) {
			throw new IllegalArgumentException("Incorrect accountContact  provided: ");
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

	public Page<Transaction> findTransactionsPaginatedByUser( String userEmail, Pageable pageable) {
		return transactionRepository.findAll(userEmail,pageable);
	}
	
	public Page<Transaction> findAllTransactionsPaginated( Pageable pageable) {
		return transactionRepository.findAll(pageable);
	}
}
