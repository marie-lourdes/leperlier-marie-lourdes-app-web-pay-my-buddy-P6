package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.BuddyAccount;

public interface IAccountRepository extends JpaRepository<BuddyAccount, Long> {

}
