package com.paymybuddy.api.controller;

import java.io.IOException;
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
import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.service.UserAccount;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UserAccountController {

	@Autowired
	private UserAccount userAccountService;

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@Valid @ModelAttribute UserApp user,HttpServletResponse response) throws IOException {
		try {
			userAccountService.createUser(user);
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}	
	}

	@PostMapping("/save-buddy-account")
	public ModelAndView createAccount(Principal principal) {
		userAccountService.addBuddyAccount(principal.getName());
		return new ModelAndView("redirect:/account-success");
	}

	@GetMapping("/sign-up")
	public String getSignUpPage(Model model) {
		UserApp userCreated = new UserApp();
		model.addAttribute("user", userCreated);
		return "sign-up";
	}

	@GetMapping("/account/home")
	public String getHomePage(Model model, Principal principal) {

		UserApp user = userAccountService.getUserEntityByEmail(principal.getName());
		model.addAttribute("user", user);
		return "home";
	}

	@GetMapping("/account/contact") // enpoint template contacts
	public String getUserContact(Model model, Principal principal) {
		List<ContactDTO> allContact = userAccountService.findUserContacts(principal.getName());
		model.addAttribute("contacts", allContact);
		return "contacts";
	}

	@GetMapping("/account/profil") //
	public String getProfilPage(Model model, Principal principal) {
		UserApp user = userAccountService.getUserEntityByEmail(principal.getName());
		// user.getEmail();
		Account userBuddyAccountBalance = userAccountService.findBuddyAccountByUser(principal.getName());
		Account userBankingAccountBalance = userAccountService.findBankingAccountByUser(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userBuddyAccount", userBuddyAccountBalance);
		model.addAttribute("userBankingAccount", userBankingAccountBalance);
		return "profil";
	}

	@GetMapping("/account-success")
	public String getSignUpSucessPage() {
		return "account-success";
	}
}