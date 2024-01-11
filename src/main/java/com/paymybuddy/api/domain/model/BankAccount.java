package com.paymybuddy.api.domain.model;

public class BankAccount extends Account{
	public BankAccount(String id,String userId,double solde){
		 super();
		 super.id=id;
		 super.userId=userId;
		 super.solde=solde;
		 super.type="buddy account";
	 }
}
