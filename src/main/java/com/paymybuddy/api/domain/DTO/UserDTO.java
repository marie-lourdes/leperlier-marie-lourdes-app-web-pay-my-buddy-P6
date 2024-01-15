package com.paymybuddy.api.domain.DTO;

import java.util.List;

import com.paymybuddy.api.domain.model.Contact;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String firstName;
	
	//private List<Contact> contacts;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String email,String firstName) {
		this.email = email;
		this.firstName = firstName;
		
	}

	@Override
	public String toString() {
		return "UserDTO{" + "email:" + email + ", firstName:" + firstName + '}';
	}
}
