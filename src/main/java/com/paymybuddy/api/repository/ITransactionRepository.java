package com.paymybuddy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByCreditUser(UserApp userId);

}

