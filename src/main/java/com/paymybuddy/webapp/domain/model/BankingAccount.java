package com.paymybuddy.webapp.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Banking Account")
public class BankingAccount extends Account {

	public BankingAccount() {
		super();
	}
}
