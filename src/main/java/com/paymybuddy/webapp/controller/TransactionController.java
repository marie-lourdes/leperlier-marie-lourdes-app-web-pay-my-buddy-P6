package com.paymybuddy.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

import jakarta.validation.Valid;

@Controller
public class TransactionController {
	
	@Autowired
	private BankingService bankingService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	UserAppService userAppService;
	

	@PostMapping("/save-payment")
	public ModelAndView createPayment(@Valid  @ModelAttribute Transaction transaction,Principal principal)
			throws IOException {
		try {
			String userPrincipal=principal.getName();
			System.out.println("principal name"+userPrincipal);
		transactionService.addTransactionUserAndContact(principal.getName(),transaction.getBeneficiaryUser().getEmail(),transaction);
	/*	bankingService.payToContact( transaction.getBeneficiaryUser().getEmail(),
					userAppService.getUserEntityByEmail(principal.getName()).getEmail(),
					transaction.getAmount(), transaction.getDescription()); */
		
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}
	
	@GetMapping("/account/transfer")
	public String getTransferPage(Model model, Principal principal) {
		System.out.println("principal name"+principal.getName());
		List<Transaction> transactions =new  ArrayList<Transaction>();
		//UserApp user = userAppService.getUserEntityByEmail(principal.getName());
		Transaction transaction=new Transaction();
		List<Transaction> transactionsFoundByUser = transactionService
				.getTransactionsByCreditUser( userAppService.getUserEntityByEmail(principal.getName()));
	/*for(Transaction transaction:transactionsFoundByUser) {
			Transaction userTransactions =new  Transaction();
			userTransactions.setBeneficiaryUser(transaction.getBeneficiaryUser());
			userTransactions.setAmount(transaction.getAmount());
			userTransactions.setDescription(transaction.getDescription());
			transactions.add(userTransactions);
		}*/

		model.addAttribute("userTransaction",transaction);
		//System.out.println("all Transaction" +  transactionsFoundByUser );
		//model.addAttribute(" transactions ", transactionsFoundByUser );
		
		/*
		 * model.addAttribute(" description", description); model.addAttribute("email",
		 * email);
		 */
		return "transfer";
	}

}
