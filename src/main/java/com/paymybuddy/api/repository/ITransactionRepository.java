package com.paymybuddy.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByCreditUser(UserApp userId);

	/* @Modifying
	 @Query(value=" insert into  Transaction  (date,creditAccount,creditUser ,beneficiaryAccount,beneficiaryUser,description, amount, transactionFees) "
	 		+ "values(:date,:credit_account_id,:credit_user_id ,:beneficiary_count_id,:beneficiary_user_id,:description,:amount,:transaction_fees)")
	public void saveTransaction(
			@Param("date ")Date date,
			@Param("credit_user_id ")UserApp creditUser,
			@Param("credit_account_id")Account creditAccount,
			@Param("beneficiary_count_id ")Account beneficiaryAccount,
			@Param("beneficiary_user_id")UserApp beneficiaryUser,
			@Param("description")String description,
			@Param("amount")double amount,
			@Param("transaction_fees")float transactionFees);*/
}

