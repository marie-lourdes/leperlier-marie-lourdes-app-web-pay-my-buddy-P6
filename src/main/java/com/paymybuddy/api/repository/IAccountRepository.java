package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Account;

import jakarta.transaction.Transactional;

@Transactional
public interface IAccountRepository extends JpaRepository<Account, Long> {
 //public BuddyAccount findByUser(String user);
}
