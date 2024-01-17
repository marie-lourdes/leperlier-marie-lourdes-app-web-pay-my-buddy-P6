package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	public Transaction findByCreditUser(String userId);
}
