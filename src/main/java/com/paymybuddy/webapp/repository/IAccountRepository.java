package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.UserApp;

public interface IAccountRepository extends JpaRepository<Account, Long> {

	public List<Account> findByUser(UserApp user);
	
//	public List<Transaction> findByTransactions(Transaction user);
	@Modifying
	@Query(value = " update  BuddyAccount a set a.balance=:amount where (a.user=:user)  ")
	public void updateBalanceBuddyAccount(@Param("amount") double amount, @Param("user") UserApp user);
	
	@Modifying
	@Query(value = " update  BankingAccount a set a.balance=:amount where (a.user=:user)  ")
	public void updateBalanceBankingAccount(@Param("amount") double amount, @Param("user") UserApp user);

}
