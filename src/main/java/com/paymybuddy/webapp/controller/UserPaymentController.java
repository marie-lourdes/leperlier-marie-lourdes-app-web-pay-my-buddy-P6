package com.paymybuddy.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.PaymentService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;
import com.paymybuddy.webapp.utils.Constants;

import jakarta.validation.Valid;

@Controller
public class UserPaymentController {
	private static final Logger log = LogManager.getLogger(UserPaymentController.class);
	
	@Autowired
	private UserAppService userAppService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionMapper transactionMapper;

	@PostMapping("/save-payment")
	public ModelAndView createPayment(@Valid @ModelAttribute Transaction userTransaction, Principal principal)
			throws IOException {
		try {
			UserDTO creditUser = userAppService.getUserByEmail(principal.getName());
			// transfert au buddyaccount si la valeur "option value est "my buddy account"
			// ou "my bankingaccount" dans le template transfer.html
			if (userTransaction.getBeneficiaryUser().getEmail().equals(Constants.BANKING_ACCOUNT)) {
				paymentService.transferMoneyToBankingAccountUser(principal.getName(), userTransaction.getAmount(),
						userTransaction.getDescription(), userTransaction);
				System.out.println("-------------------transferMoneyToBankingAccountUser-------------");
				
				transactionService.addTransaction( creditUser.getId(),  creditUser.getEmail(), userTransaction);
				
			} else if (userTransaction.getBeneficiaryUser().getEmail().equals(Constants.BUDDY_ACCOUNT)) {
				paymentService.transferMoneyToBuddyAccountUser(principal.getName(), userTransaction.getAmount(),
						userTransaction.getDescription(), userTransaction);
				System.out.println("-------------------transferMoneyToBuddyAccountUser-------------");
				
				transactionService.addTransaction( creditUser.getId(),  creditUser.getEmail(), userTransaction);
			} else {
				paymentService.payToContact(userAppService.getUserByEmail(principal.getName()).getEmail(),
						userTransaction.getBeneficiaryUser().getEmail(), userTransaction.getAmount(),
						userTransaction.getDescription(), userTransaction);
				System.out.println("-------------------payToContact-------------");
			transactionService.addTransaction(creditUser.getId(), userTransaction.getBeneficiaryUser().getEmail(), userTransaction);
			}

			return new ModelAndView("redirect:/transfer-success");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
		catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ModelAndView();
		}
	}

	@GetMapping("/home/transfer")
	public String getTransferPage(Model model, Principal principal) {
		String userEmail = principal.getName();

		List<UserApp> allContacts = userAppService.getAllUserContacts(userEmail);
		this.getHistoricalTransactionsByUser(1, model, principal);

		String breadcrumbTransfer = "Transfer";
		model.addAttribute("breadcrumbTransfer", breadcrumbTransfer);
		model.addAttribute("contacts", allContacts);
		return "transfer";
	}

	@GetMapping("/page/{pageNber}")
	public String getHistoricalTransactionsByUser(@PathVariable int pageNber, Model model, Principal principal) {
		String userEmail = principal.getName();
		Transaction userTransaction = new Transaction();
		List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();

		int pageSize = 3;
		Page<Transaction> page = paymentService.getTransactionsByUser(pageNber, pageSize, userEmail);
		List<Transaction> listTransactions = page.getContent();
		for (Transaction transaction : listTransactions) {
			TransactionDTO transactionUser = transactionMapper.transactionToTransactionDTO(transaction);
			if (transaction.getBeneficiaryUser().getEmail().equals(userEmail)) {
				transactionUser.setContactName("Me");
			}
			transactions.add(transactionUser);

		}

		model.addAttribute("currentPage", pageNber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("userTransaction", userTransaction);
		model.addAttribute("transactions", transactions);
		return "transfer";
	}

	@GetMapping("/transfer-success")
	public String getTransferSucessPage() {
		return "transfer-success";
	}

}