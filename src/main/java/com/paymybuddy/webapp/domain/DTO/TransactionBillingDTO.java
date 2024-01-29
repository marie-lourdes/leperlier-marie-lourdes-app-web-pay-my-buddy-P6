package com.paymybuddy.webapp.domain.DTO;

public class TransactionBillingDTO {
	private String userCreditName;
	private String contactName ;
	private String description ;
	private  long amount ;	
	private double fees ;
	
	public TransactionBillingDTO(){}
	
	public TransactionBillingDTO(String userCreditName,String contactName ,String description,long amount,double fees  ){
		this.userCreditName=userCreditName;
		this.contactName=contactName;
		this.description=description;
		this.amount=amount;
		this.fees=fees;
	}

	@Override
	public String toString() {
		return "TransactionDTO{" + "contactName:" + contactName + ", description :" + description  + ", amount:" + amount+ '}';
	}
}
