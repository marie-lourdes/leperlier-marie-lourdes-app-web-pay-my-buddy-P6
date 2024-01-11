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

	protected long id;

	
	protected String userId;

	protected double solde;
	

	protected String type;
	
	public Account(long id,String userId,double solde){
		 this.id=id;
			this.userId=userId;
			 this.solde=solde;
	}
	@Override
	public String toString() {
		return "Account{" + "email:" + id+ ", userid:'" + userId + '\'' + ", solde:" + solde + ", type:" + type + '}';
	}
}
