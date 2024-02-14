package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.UserApp;

public interface IAccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByUser(UserApp user);

	@Modifying
	@Query(" update  BuddyAccount a set a.balance= ?1 where (a.user= ?2)  ")
	 void updateBalanceBuddyAccount(@Param("amount") double amount, @Param("user") UserApp user);

	@Modifying
	@Query(" update  BankingAccount a set a.balance= ?1 where (a.user= ?2)  ")
	 void updateBalanceBankingAccount(@Param("amount") double amount, @Param("user") UserApp user);

}
