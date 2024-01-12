package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class BankAccount extends Account{

	@Id
	private long id;

	@NotNull
	@OneToMany
	@JoinColumn(name="user_id")
	private List<UserApp> user;

	@NotNull
	@Column(name = "balance")
	private double balance;
	
	@NotNull
	@Column(name = "type")
	private String type ="banking account";
	
	public BankAccount() {
		super();
	}
	public BankAccount(long id, List<UserApp>  user, double balance){
		super( id, user, balance);
		this.id=id;
		this.user=user;
		this.balance=balance;
	 }
}
