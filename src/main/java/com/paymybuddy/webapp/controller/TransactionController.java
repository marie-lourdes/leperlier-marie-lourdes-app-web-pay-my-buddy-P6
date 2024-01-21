package com.paymybuddy.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

@Controller
public class TransactionController {
	
	/*@Autowired
	private BankingService bankingService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	UserAppService userAppService;*/
	
/*	@PostMapping("/save-payment")
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
	
			/*transactionService.addTransactionUserAndContact(transaction);
			bankingService.payToContact( transaction.getBeneficiaryUser().getEmail(),
					userAppService.getUserEntityByEmail(principal.getName()).getEmail(),
					transaction.getAmount(), transaction.getDescription()); 
		
			return new ModelAndView("redirect:/");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			// response.setIntHeader("status",400);
			return new ModelAndView("redirect:/error-400");
		}
	}*/

}
