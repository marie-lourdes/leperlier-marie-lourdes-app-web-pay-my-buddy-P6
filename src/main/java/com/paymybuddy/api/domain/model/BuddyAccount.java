package com.paymybuddy.api.domain.model;

public class BuddyAccount extends Account {
	
	public BuddyAccount(String id,String userId,double solde){
		 super();
		 super.id=id;
		 super.userId=userId;
		 super.solde=solde;
		 super.type="buddy account";
	 }

}
