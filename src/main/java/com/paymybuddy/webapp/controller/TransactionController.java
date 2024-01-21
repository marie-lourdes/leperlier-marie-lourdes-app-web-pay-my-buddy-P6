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
	public ModelAndView createPayment(@Valid @ModelAttribute Transaction transaction,Principal principal)
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

			bankingService.payToContact( transaction.getBeneficiaryUser().getEmail(),
					userAppService.getUserEntityByEmail(principal.getName()).getEmail(),
					transaction.getAmount(), transaction.getDescription()); 
		
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}
	@GetMapping("/account/transfer")
	public String gettransfer(Model model, Principal principal) {
		Transaction transactionCreated = new Transaction();
	/* double  amount=0;
		String email="";
		String description="";*/
		
		model.addAttribute(" transactionCreated ", transactionCreated );
		/*model.addAttribute(" description",  description);
		model.addAttribute("email", email);*/
		return "transfer";
	}
}
