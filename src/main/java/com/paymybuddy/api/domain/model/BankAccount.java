package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class BankAccount extends Account{

	@Id
	@NotNull
	protected long id;

	@NotNull
	@Column(name = "user_id")
	protected String userId;

	@NotNull
	@Column(name = "solde")
	protected double solde;
	
	@NotNull
	@Column(name = "type")
	protected String type;
	
	public BankAccount(long id,String userId, double solde){
		super( id, userId,solde);
		this.id=id;
		this.userId=userId;
		this.solde=solde;
		this.type="banking account";		 
	 }
}
