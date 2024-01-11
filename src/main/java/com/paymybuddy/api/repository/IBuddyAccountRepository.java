package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.BuddyAccount;

import jakarta.transaction.Transactional;


public interface IBuddyAccountRepository extends JpaRepository<BuddyAccount, Long> {
 //public BuddyAccount findByUser(String user);
}
