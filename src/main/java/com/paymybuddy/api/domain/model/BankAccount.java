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
public class BankAccount extends Account{

	@Id
	private long id;

	@NotNull
	@OneToOne
	@JoinColumn(name="user_id")
	private String user;

	@NotNull
	@Column(name = "balance")
	private double balance;
	
	@NotNull
	@Column(name = "type")
	private String type ="banking account";
	
	public BankAccount() {
		super();
	}
	public BankAccount(long id, String user, double balance){
		super( id, user, balance);
		this.id=id;
		this.user=user;
		this.balance=balance;
	 }
}
