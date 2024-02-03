package com.paymybuddy.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.DTO.TransactionDTO;
import com.paymybuddy.webapp.domain.DTO.TransactionMapper;
import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.AccountService;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.IRole;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;
import com.paymybuddy.webapp.utils.Constants;

import jakarta.validation.Valid;
import lombok.Data;

@Data
@Controller
public class UserAccountController {

	@Autowired
	@Qualifier("roleImpl")
	private IRole role;
	
	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@Valid @ModelAttribute UserApp user) throws IOException {
		try {
			userAppService.createUser(user);
			return new ModelAndView("redirect:/home");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error");
		}
	}

	@PostMapping("/save-buddy-account")
	public ModelAndView createAccount(Principal principal) {
		try {
			accountService.addBuddyAccount(principal.getName());
			return new ModelAndView("redirect:/account-success");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error");
		}

	}

	@PostMapping("/save-contact")
	public ModelAndView createContact(@Valid @ModelAttribute UserApp contact, Principal principal) throws IOException {

		try {
			userAppService.addUserContact(contact.getEmail(), principal.getName());
			return new ModelAndView("redirect:/home/contact");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error");
		}
	}

	@GetMapping("home/sign-up")
	public String getSignUpPage(Model model) {
		UserApp userCreated = new UserApp();
		
		String breadcrumbSignUp= "Sign Up";
		model.addAttribute("breadcrumbSignUp",breadcrumbSignUp);
		model.addAttribute("user", userCreated);
		return "sign-up";
	}

	@GetMapping("/home")
	public String getHomePage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "home");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		UserDTO user = userAppService.getUserByEmail(principal.getName());
		String breadcrumbHome= "Home /";
		model.addAttribute("breadcrumbHome",breadcrumbHome);
		model.addAttribute("user", user);
		return "home";
	}

	@GetMapping("/home/contact") // enpoint template contacts
	public String getUserContact(Model model, Principal principal) {
		List<UserApp> allContact = userAppService.getAllUserContacts(principal.getName());
		
		String breadcrumbContact= "Contact";
		model.addAttribute("breadcrumbContact",breadcrumbContact);
		model.addAttribute("contacts", allContact);
		return "contacts";
	}

	@GetMapping("/home/profil") //
	public String getProfilPage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "profil");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		UserDTO contactCreated = new UserDTO();
		UserDTO user = userAppService.getUserByEmail(principal.getName());
		BuddyAccount userBuddyAccountBalance = accountService.findBuddyAccountByUser(user.getEmail());
		BankingAccount userBankingAccountBalance = accountService.findBankingAccountByUser(user.getEmail());
		
		String breadcrumbProfil= "Profil";
		model.addAttribute("breadcrumbProfil",breadcrumbProfil);
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

	public String isUserOrAdmin(Model model, Principal principal, String view) throws Exception {
		return role.verifRolePrincipalInView(model, principal, view);
	}
}