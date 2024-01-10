package com.paymybuddy.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paymybuddy.api.domain.DTO.ContactDTO;
import com.paymybuddy.api.domain.DTO.UserDTO;
import com.paymybuddy.api.service.UserAccount;

@Controller
public class UserAccountController {
	
	@Autowired
	private UserAccount userAccountService ;
	
	private String principalUsername;

	/*-----------------Controller API------------------------------*/	
	@GetMapping("/account/{id}")
	@ResponseBody
	public ResponseEntity<UserDTO> getHomePage( @PathVariable  String id) {
		UserDTO userFoundById =userAccountService.getUserByEmail(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(userFoundById);
	}
	
/*	@GetMapping("/account/{id}/contact")// enpoint ressource contact , pas endpoint template!
	@ResponseBody
	public ResponseEntity<List<ContactDTO>> getUserContact(@PathVariable  String id ) {
		List<ContactDTO> allContact = userAccount.findUserContacts(id); 
		return  ResponseEntity.status(HttpStatus.OK).body(allContact );
	}*/

	/*-----------------Controller web------------------------------*/
	/*@GetMapping("/")
	@ResponseBody
	public void getPrincipalUserInfo(Principal principal) {
		this.principalUsername= principal.getName();
		
	}*/
	
	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "sign-up";
	}
	
	@GetMapping("/account/home")
	public String getHomePage( Model model) {
		String principal = this.getPrincipalUsername();
		model.addAttribute("principal",principal);
		return "home";
	}
	
	@GetMapping("/account/contact")// enpoint template contacts
	public String getUserContact(Model model) {
		List<ContactDTO> allContact = userAccountService.findUserContacts(this.getPrincipalUsername());
		model.addAttribute("contacts", allContact );
		return "contacts";
	}

	public String getPrincipalUsername() {
		return this. principalUsername;
	}
}