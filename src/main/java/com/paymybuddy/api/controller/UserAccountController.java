package com.paymybuddy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paymybuddy.api.domain.DTO.ContactDTO;
import com.paymybuddy.api.service.UserAccount;

@Controller
public class UserAccountController {
	@Autowired
	private UserAccount userAccount ;
	
	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "sign-up";
	}
	
	@GetMapping("/account/home")
	public String getHomePage() {
		return "home";
	}
	
	@GetMapping("/account/{id}/contact")// enpoint ressource contact , pas endpoint template!
	@ResponseBody
	public ResponseEntity<List<ContactDTO>> getUserContact(@PathVariable  String id ) {
		List<ContactDTO> allContact = userAccount.findUserContacts(id); 
		return  ResponseEntity.status(HttpStatus.CREATED).body(allContact );
	}

}
