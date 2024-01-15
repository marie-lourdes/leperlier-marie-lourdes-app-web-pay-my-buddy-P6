package com.paymybuddy.api.domain.DTO;

import lombok.Data;

@Data
public class ContactDTO {
	private String idContact;
	private String firstName;
	private String lastName;
	
	public ContactDTO() {
		
	}
	
	public ContactDTO(String idContact, String firstName, String lastName) {
		this.idContact = idContact;
		this.firstName = firstName;
		this.lastName=lastName;
	}

	@Override
	public String toString() {
		return "ContactDTO{" + "idContact:" + idContact+ ", firstName:" + firstName +", lastName:" + lastName + '}';
	}
}
