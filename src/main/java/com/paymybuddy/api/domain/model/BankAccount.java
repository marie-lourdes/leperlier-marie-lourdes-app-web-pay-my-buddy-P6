package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class BankAccount extends Account{

	@Id
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserApp user;

	@NotNull
	@Column(name = "balance")
	private double balance;
	
	@NotNull
	@Column(name = "type")
	private String type ="banking account";
	
	public BankAccount() {
		super();
	}
	public BankAccount(long id, UserApp  user, double balance){
		super( id, user, balance);
		this.id=id;
		this.user=user;
		this.balance=balance;
	 }
}
