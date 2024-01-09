package com.paymybuddy.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paymybuddy.api.domain.DTO.ContactDTO;
import com.paymybuddy.api.service.AuthenticationUserDetailService;
import com.paymybuddy.api.service.UserAccount;

@Controller
public class UserAccountController {
	
	@Autowired
	private UserAccount userAccountService ;
	
	private String principalUsername;

	@GetMapping("/")
	@ResponseBody
	public void getPrincipalUserInfo(Principal principal) {
		this.principalUsername= principal.getName();
	}
	
	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "sign-up";
	}
	
	@GetMapping("/account/home")
	public String getHomePage() {
		return "home";
	}
	
/*	@GetMapping("/account/{id}/contact")// enpoint ressource contact , pas endpoint template!
	@ResponseBody
	public ResponseEntity<List<ContactDTO>> getUserContact(@PathVariable  String id ) {
		List<ContactDTO> allContact = userAccount.findUserContacts(id); 
		return  ResponseEntity.status(HttpStatus.OK).body(allContact );
	}*/
	
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