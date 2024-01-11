package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class BuddyAccount extends Account {
	
	@Id
	private long id;

	@NotNull
	@OneToOne
	@JoinColumn(name="user_id")
	private UserApp user;

	@NotNull
	@Column(name = "balance")
	private double balance;
	
	@NotNull
	@Column(name = "type")
	private String type="buddy account";
	
	public BuddyAccount() {
		super();
	}
	public BuddyAccount(UserApp user, double balance){
		super(user, balance);
		this.user=user;
		this.balance=balance;
	 }

}
