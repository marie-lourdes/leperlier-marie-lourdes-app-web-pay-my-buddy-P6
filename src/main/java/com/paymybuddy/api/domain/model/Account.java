package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public abstract class Account {

	private long id;
	private UserApp user;
	private double solde;
	private String type;
	
	public Account(){}
	
	public Account(UserApp user,double solde){
		this.user=user;
		this.solde=solde;
	}
	
	public Account(long id,UserApp user,double solde){
		 this.id=id;
		this.user=user;
		this.solde=solde;
	}
	@Override
	public String toString() {
		return "Account{" + "email:" + id+ ", user:'" + user + '\'' + ", solde:" + solde + ", type:" + type + '}';
	}
}
