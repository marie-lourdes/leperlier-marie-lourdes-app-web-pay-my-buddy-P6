package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class BuddyAccount {
	
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
	private String type;
	
	
	/*public BuddyAccount(UserApp user, double balance,String type){
		this.user=user;
		this.balance=balance;
		this.type=type;
	 }*/

}
