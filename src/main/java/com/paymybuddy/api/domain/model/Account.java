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
	private List<UserApp> user;
	private double balance;
	private String type;
	
	public Account(){}
	
	public Account(List<UserApp> user,double balance){
		this.user=user;
		this.balance=balance;
	}
	
	public Account(long id,List<UserApp> user,double balance){
		 this.id=id;
		this.user=user;
		this.balance=balance;
	}
	@Override
	public String toString() {
		return "Account{" + "id:" + id+ ", user:'" + user + '\'' + ", balance:" + balance+ ", type:" + type + '}';
	}
}
