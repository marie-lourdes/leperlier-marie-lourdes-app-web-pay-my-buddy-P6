package com.paymybuddy.api.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.api.domain.model.Contact;

@Component
public class ContactMapper {

	public ContactDTO contactToContactDTO(Contact contact) {
		String email = contact.getIdContact();
		String firstName = contact.getFirstName();
		String lastName = contact.getLastName();

		return new ContactDTO(email, firstName, lastName);
	}

}
