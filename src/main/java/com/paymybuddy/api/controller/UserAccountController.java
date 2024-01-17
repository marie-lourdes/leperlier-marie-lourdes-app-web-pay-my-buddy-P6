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

import com.paymybuddy.api.domain.DTO.UserDTO;
import com.paymybuddy.api.domain.model.Account;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.service.UserAppService;
import com.paymybuddy.api.service.banking.AccountService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UserAccountController {

	@Autowired
	private UserAppService userAppService;
	
	@Autowired
	private AccountService  accountService ;

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@Valid @ModelAttribute UserApp user, HttpServletResponse response)
			throws IOException {
		try {
			userAppService.createUser(user);
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}

	@PostMapping("/save-buddy-account")
	public ModelAndView createAccount(Principal principal) {
		try {
			accountService.addBuddyAccount(principal.getName());
			return new ModelAndView("redirect:/account-success");
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error-400");
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error-400");
		}
	
		
	}

	@PostMapping("/save-contact")
	public ModelAndView createContact(@ModelAttribute UserApp contact, Principal principal) throws IOException {

		try {
			userAppService.addUserContact(contact.getEmail(), principal.getName());
			return new ModelAndView("redirect:/account/contact");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error-400");
		}
	}

	@GetMapping("/sign-up")
	public String getSignUpPage(Model model) {
		UserApp userCreated = new UserApp();
		model.addAttribute("user", userCreated);
		return "sign-up";
	}

	@GetMapping("/account/home")
	public String getHomePage(Model model, Principal principal) {
		UserApp user = userAppService.getUserEntityByEmail(principal.getName());
		model.addAttribute("user", user);
		return "home";
	}

	@GetMapping("/account/contact") // enpoint template contacts
	public String getUserContact(Model model, Principal principal) {
		List<UserApp> allContact = userAppService.findAllUserContacts(principal.getName());
		model.addAttribute("contacts", allContact);
		return "contacts";
	}

	@GetMapping("/account/profil") //
	public String getProfilPage(Model model, Principal principal) {
		UserDTO contactCreated = new UserDTO();

		UserApp user = userAppService.getUserEntityByEmail(principal.getName());
		Account userBuddyAccountBalance = accountService.findBuddyAccountByUser(user.getEmail());
		Account userBankingAccountBalance = accountService.findBankingAccountByUser(user.getEmail());
		model.addAttribute("contact", contactCreated);
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