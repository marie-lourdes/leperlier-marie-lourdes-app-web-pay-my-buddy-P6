package com.paymybuddy.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.AccountService;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

import jakarta.validation.Valid;

@Controller
public class UserAccountController {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private BankingService bankingService;

	@PostMapping("/sign-up-form")
	public ModelAndView createUser(@Valid @ModelAttribute UserApp user)
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
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error-400");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/error-400");
		}

	}

	@PostMapping("/save-contact")
	public ModelAndView createContact(@Valid @ModelAttribute UserApp contact,Principal principal) throws IOException {

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
		BuddyAccount userBuddyAccountBalance = accountService.findBuddyAccountByUser(user.getEmail());
		BankingAccount userBankingAccountBalance = accountService.findBankingAccountByUser(user.getEmail());
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
	
	@PostMapping("/save-payment")
	public ModelAndView createPayment(@Valid  @ModelAttribute Transaction transaction,Principal principal)
			throws IOException {
		try {
			//double feesTransaction = Billing.calculateFees(amount);
	/*	Transaction transactionCreated = new Transaction();
			transactionCreated.setDate(new Date());
			transactionCreated.setCreditUser(userAppService.getUserEntityByEmail(principal.getName()));
			transactionCreated.setBeneficiaryUser(userAppService.getUserEntityByEmail(email));
			transactionCreated.setAmount(amount);
			transactionCreated.setTransactionFees(feesTransaction);
			transactionService.saveTransactionDB(transactionCreated);*/
	
		//	transactionService.addTransactionUserAndContact(userId,contactId,transaction);
			//transactionService.addTransactionUserAndContact(principal.getName(),transaction.getBeneficiaryUser().getEmail(),transaction);
			bankingService.payToContact( transaction.getBeneficiaryUser().getEmail(),
					userAppService.getUserEntityByEmail(principal.getName()).getEmail(),
					transaction.getAmount(), transaction.getDescription()); 
		
			return new ModelAndView("redirect:/account/transfer");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}
	
	@GetMapping("/account/transfer")
	public String getTransferPage(Model model, Principal principal) {
		//Transaction transaction =new  Transaction();
		//UserApp user = userAppService.getUserEntityByEmail(principal.getName());
		List<Transaction> transactions=new ArrayList<>();
		List<Transaction> transactionsFoundByUser = transactionService
				.getTransactionsByCreditUser( userAppService.getUserEntityByEmail(principal.getName()));
		for(Transaction transaction:transactionsFoundByUser) {
			Transaction userTransactions =new  Transaction();
			userTransactions.setBeneficiaryUser(transaction.getBeneficiaryUser());
			userTransactions.setAmount(transaction.getAmount());
			userTransactions.setDescription(transaction.getDescription());
			transactions.add(userTransactions);
		}

		model.addAttribute(" transaction ", new  Transaction());
		System.out.println("all Transaction" + transactions);
		model.addAttribute(" transactions ", transactions);
		
		/*
		 * model.addAttribute(" description", description); model.addAttribute("email",
		 * email);
		 */
		return "transfer";
	}
}