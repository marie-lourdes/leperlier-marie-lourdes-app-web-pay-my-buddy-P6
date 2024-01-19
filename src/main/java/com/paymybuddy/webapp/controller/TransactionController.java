package com.paymybuddy.webapp.controller;



import java.io.IOException;
import java.util.Date;

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
import com.paymybuddy.webapp.utils.Billing;

import jakarta.validation.Valid;

@Controller
public class TransactionController {
	@Autowired
	private BankingService bankingService;
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/save-payment")
	public ModelAndView createUser(@Valid @ModelAttribute Transaction transaction, Principal principal )
			throws IOException {
		try {
			double feesTransaction = Billing.calculateFees(transaction.getAmount());
			Transaction transactionCreated = new Transaction(new Date(), transaction.getCreditUser(), transaction.getBeneficiaryUser(),  transaction.getDescription(),
					transaction.getAmount(), feesTransaction);
			bankingService.payToContact(transaction.getBeneficiaryUser().getFirstName(),transaction.getBeneficiaryUser().getLastName(), principal.getName(), transaction.getAmount(), transaction.getDescription()); 
			transactionService.saveTransactionDB(transactionCreated);
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}
	@GetMapping("/account/transfer")
	public String getSignUpPage(Model model) {
		Transaction transactionCreated = new Transaction();
		model.addAttribute("transaction", transactionCreated);
		return "transfer";
	}
}
