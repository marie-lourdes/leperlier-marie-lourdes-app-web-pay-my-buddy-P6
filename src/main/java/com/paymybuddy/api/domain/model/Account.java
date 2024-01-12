package com.paymybuddy.api.domain.model;

import lombok.Data;

@Data
public abstract class Account {

	private long id;
	private UserApp user;
	private double balance;
	private String type;
	
	public Account(){}
	
	public Account(UserApp user,double balance){
		this.user=user;
		this.balance=balance;
	}
	
	public Account(long id,UserApp user,double balance){
		 this.id=id;
		this.user=user;
		this.balance=balance;
	}
	@Override
	public String toString() {
		return "Account{" + "id:" + id+ ", user:'" + user + '\'' + ", balance:" + balance+ ", type:" + type + '}';
	}
}
