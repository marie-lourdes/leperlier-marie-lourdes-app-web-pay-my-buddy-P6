package com.paymybuddy.webapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.paymybuddy.webapp.domain.DTO.TransactionBillingDTO;
import com.paymybuddy.webapp.domain.DTO.TransactionMapper;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	private  AdminService  adminService;

	@Autowired
	private TransactionMapper transactionMapper;
	
	@GetMapping("/admin/transactions-billing")
	public String getHistoricalTransactionsWithFees( Model model, Principal principal) {
		this.getransactionsPaginated(1, model, principal);
		return "transactions-billing";
	}
	
	@GetMapping("/admin/page/{pageNber}")
	public String getransactionsPaginated(@PathVariable int pageNber, Model model, Principal principal) {
		String userEmail = principal.getName();
		Transaction userTransaction = new Transaction();
		List<TransactionBillingDTO> transactions = new ArrayList<TransactionBillingDTO>();
		
		int pageSize = 10;
	    Page <Transaction > page =  adminService.getAllTransactionsWithFees(pageNber, pageSize);
	    List <Transaction > listTransactions  = page.getContent();	
		for (Transaction transaction : listTransactions) {
			TransactionBillingDTO transactionUser = transactionMapper.transactionToTransactionBillingDTO(transaction);	
			
				transactions.add(transactionUser);
			
		}

	    model.addAttribute("currentPage", pageNber);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("userTransaction", userTransaction);
		model.addAttribute("transactions", transactions);
		return "transactions-billing";
		}
}
