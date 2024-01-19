package com.paymybuddy.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;
import com.paymybuddy.webapp.utils.Billing;

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
	public ModelAndView createPayment(@Valid @ModelAttribute Transaction transaction, Principal principal )
			throws IOException {
		try {
			double feesTransaction = Billing.calculateFees(transaction.getAmount());
			

			bankingService.payToContact(transaction.getBeneficiaryUser().getEmail(), principal.getName(), transaction.getAmount(), transaction.getDescription()); 
			//transactionService.saveTransactionDB(transaction);
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}
	@GetMapping("/account/transfer")
	public String gettransfer(Model model) {
		Transaction transactionCreated = new Transaction();
		
		model.addAttribute("transaction", transactionCreated);
		return "transfer";
	}
}
