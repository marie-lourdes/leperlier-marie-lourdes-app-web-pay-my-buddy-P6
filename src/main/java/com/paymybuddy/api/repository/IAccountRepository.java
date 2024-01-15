package com.paymybuddy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.UserApp;


public interface IAccountRepository extends JpaRepository<Account, Long> {
 public List< Account> findByUser(UserApp user);
}
