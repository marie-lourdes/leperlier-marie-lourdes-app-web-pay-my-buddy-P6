package com.paymybuddy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.UserApp;

public interface IAccountRepository extends JpaRepository<Account, Long> {
 public List< Account> findByUser(UserApp user);
 @Modifying
 @Query(value=" update  Account a set a.balance=:amount where (a.user=:user) and(a.type like '%Buddy Account%') ")
 public void updateBalanceBuddyAccount(@Param("amount") double amount, @Param("user") UserApp user);
}
