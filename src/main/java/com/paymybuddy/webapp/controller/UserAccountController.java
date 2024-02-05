package com.paymybuddy.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.AccountFactory;
import com.paymybuddy.webapp.AccountFactory.AccountType;
import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.IRole;
import com.paymybuddy.webapp.service.UserAccountService;

import jakarta.validation.Valid;
import lombok.Data;

@Data
@Controller
public class UserAccountController {
	private static final Logger log = LogManager.getLogger(UserAccountController.class);

	@Autowired
	@Qualifier("roleImpl")
	private IRole role;

	@Autowired
	private UserAccountService userAccountService;

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@Valid @ModelAttribute UserApp user) throws IOException {
		try {
		userAccountService.createUser(user);
		return new ModelAndView("redirect:/home");
		} catch (IllegalArgumentException e) {
			 log.error(e.getMessage());
			return new ModelAndView("redirect:/error");
		}
	}

	@PostMapping("/save-contact")
	public ModelAndView createContact(@Valid @ModelAttribute UserApp contact, Principal principal) throws IOException {

		try {
			userAccountService.addUserContact(contact.getEmail(), principal.getName());
			return new ModelAndView("redirect:/home/contact");
		} catch (IllegalArgumentException e) {
			 log.error(e.getMessage());
			return new ModelAndView("redirect:/error");
		} catch (NullPointerException e) {
			 log .error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@PostMapping("/save-buddy-account")
	public ModelAndView createAccount(Principal principal) {
		try {
			BuddyAccount buddyAccountCreated =(BuddyAccount) AccountFactory.makeAccount(AccountType.BUDDY);
			buddyAccountCreated=userAccountService.addBuddyAccount(principal.getName());
			return new ModelAndView("redirect:/account-success");

		} catch (Exception e) {
			 log .error(e.getMessage());
			return new ModelAndView("redirect:/error");
		} 
	}

	@GetMapping("/home")
	public String getHomePage(Model model, Principal principal) {
		UserDTO user = new UserDTO();
		try {
			this.isUserOrAdmin(model, principal, "home");
			user = userAccountService.getUserByEmail(principal.getName());

			String breadcrumbHome = "Home /";
			model.addAttribute("breadcrumbHome", breadcrumbHome);
			model.addAttribute("user", user);
		} catch (Exception e) {
			log.error("Failed to retrieve homepage " + e.getMessage());
			return "error";
		}
		log.info(" Homepage successfull retrieved");
		return "home";
	}

	@GetMapping("/home/sign-up")
	public String getSignUpPage(Model model) {
		UserApp userCreated = new UserApp();
		try {
			String breadcrumbSignUp = "Sign Up";
			model.addAttribute("breadcrumbSignUp", breadcrumbSignUp);
			model.addAttribute("user", userCreated);

		} catch (Exception e) {
			log.error("Failed to retrieve sign up page " + e.getMessage());
			return "error";
		}
		log.info(" Sign up page successfull retrieved");
		return "sign-up";
	}

	@GetMapping("/home/contact") // enpoint template contacts
	public String getUserContact(Model model, Principal principal) {
		try {
			List<UserApp> allContact = userAccountService.getAllUserContacts(principal.getName());

			String breadcrumbContact = "Contact";
			model.addAttribute("breadcrumbContact", breadcrumbContact);
			model.addAttribute("contacts", allContact);
		}catch (Exception e) {
			log.error("Failed to retrieve contact page " + e.getMessage());
		}
		log.info("Contact page  successfull retrieved");
		return "contact";
	}

	@GetMapping("/home/profil") //
	public String getProfilPage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "profil");

			UserDTO contactCreated = new UserDTO();
			UserDTO user = userAccountService.getUserByEmail(principal.getName());
			BuddyAccount userBuddyAccountBalance = userAccountService.findBuddyAccountByUser(user.getEmail());
			BankingAccount userBankingAccountBalance = userAccountService.findBankingAccountByUser(user.getEmail());

			String breadcrumbProfil = "Profil";
			model.addAttribute("breadcrumbProfil", breadcrumbProfil);
			model.addAttribute("contact", contactCreated);
			model.addAttribute("user", user);
			model.addAttribute("userBuddyAccount", userBuddyAccountBalance);
			model.addAttribute("userBankingAccount", userBankingAccountBalance);
		} catch (Exception e) {
			log.error("Failed to retrieve profil page" + e.getMessage());
			return "error";
		}
		log.info("Profil page  successfull retrieved");
		return "profil";
	}

	@GetMapping("/account-success")
	public String getSignUpSucessPage() {
		return "account-success";
	}

	public void isUserOrAdmin(Model model, Principal principal, String view) {
		try {
			role.verifRolePrincipalInView(model, principal, view);
		} catch (Exception e) {
			log.error("Failed to retrieve role admin and role user in view {}" + e.getMessage(), view);
		
		}
	}
}