package com.paymybuddy.api.domain.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.domain.model.UserApp;

@Component
public class ContactMapper {
	public ContactDTO contactToContactDTO(Contact contact) {

		String email = contact.getIdContact();
		  String firstName = contact.getFirstName();
		String lastName= contact.getLastName();
	  
		
		return new ContactDTO (email,firstName,lastName);
	}
	

}
