package com.paymybuddy.api.domain.DTO;

import lombok.Data;

@Data
public class ContactDTO {
	private String emailContact ;
	private String firstName;
	private String lastName;
	
	public ContactDTO() {
		
	}
	
	public ContactDTO(String emailContact , String firstName, String lastName) {
		this.emailContact = emailContact ;
		this.firstName = firstName;
		this.lastName=lastName;
	}

	@Override
	public String toString() {
		return "ContactDTO{" + "emailContact :" + emailContact + ", firstName:" + firstName +", lastName:" + lastName + '}';
	}
}
