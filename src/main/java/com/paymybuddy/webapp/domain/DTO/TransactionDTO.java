package com.paymybuddy.webapp.domain.DTO;

import lombok.Data;

@Data
public class TransactionDTO {
	private String contactName ;
	private String description ;
	private  long amount ;
	
	public TransactionDTO(){}
	public TransactionDTO(String contactName ,String description,long amount  ){
		this.contactName=contactName;
		this.description=description;
		this.amount=amount;
	}

	@Override
	public String toString() {
		return "TransactionDTO{" + "contactName:" + contactName + ", description :" + description  + ", amount:" + amount+ '}';
	}
}
