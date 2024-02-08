package com.paymybuddy.webapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.webapp.domain.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	
	 @Query(value = " select  t from Transaction t  where t.creditUser.email=:email  ")
	 Page<Transaction> findAll( @Param("email") String email,Pageable pageable);
	 
	 Page<Transaction> findAll( Pageable pageable);
}

