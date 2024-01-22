package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;

import jakarta.transaction.Transactional;
@Transactional
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	//@Query(value = " select  t from Transaction t  where t.creditUser.email=:email  ")
	public List<Transaction> findByCreditUser( UserApp user);

}

