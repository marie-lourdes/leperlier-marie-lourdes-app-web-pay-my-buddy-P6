package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.BankAccount;

public interface IBankAccountRepository extends JpaRepository<BankAccount, Long>{

}
