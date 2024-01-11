package com.paymybuddy.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.api.domain.DTO.ContactDTO;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.service.UserAccount;

@Controller
public class UserAccountController {
	
	@Autowired
	private UserAccount userAccountService ;
	

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@ModelAttribute UserApp user){
		 userAccountService.createUser(user);
		 return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/sign-up")
	public String getSignUpPage(Model model) {
		UserApp userCreated = new UserApp();
		model.addAttribute("user",userCreated);
		return "sign-up";
	}
	
	@GetMapping("/account/home")
	public String getHomePage( Model model, Principal principal) {
	
		model.addAttribute("principal",principal.getName());
		return "home";
	}
	
	@GetMapping("/account/contact")// enpoint template contacts
	public String getUserContact(Model model, Principal principal) {
		List<ContactDTO> allContact = userAccountService.findUserContacts(principal.getName());
		model.addAttribute("contacts", allContact );
		return "contacts";
	}
	
	@GetMapping("/account/profil")// 
	public String getProfilPage(Model model, Principal principal) {
		UserApp user = userAccountService.getUserEntityByEmail(principal.getName());
		user.getEmail();
	    double userAccountBalance= userAccountService.findBuddyAccountBalanceByUser(user.getEmail());
		model.addAttribute("user", user );
		model.addAttribute("userAccountBalance", userAccountBalance);
		return "profil";
	}

}