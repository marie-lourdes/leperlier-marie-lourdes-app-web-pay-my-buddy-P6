package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class BuddyAccount extends Account {
	
	@Id
	@NotNull
	private long id;

	@NotNull
	@Column(name = "user_id")
	private String userId;

	@NotNull
	@Column(name = "solde")
	private double solde;
	
	@NotNull
	@Column(name = "type")
	private String type="buddy account";
	
	public BuddyAccount() {
		super();
	}
	public BuddyAccount(long id,String userId,double solde){
		super( id, userId,solde);
		this.id=id;
		this.userId=userId;
		this.solde=solde;
	 }

}
