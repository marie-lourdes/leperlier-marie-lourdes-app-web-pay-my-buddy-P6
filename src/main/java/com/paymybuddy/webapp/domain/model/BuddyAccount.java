package com.paymybuddy.webapp.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Buddy Account")
public class BuddyAccount extends Account {
	public BuddyAccount () {super();}
}
