package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByAccountCreditUser(Account accountId);

}
