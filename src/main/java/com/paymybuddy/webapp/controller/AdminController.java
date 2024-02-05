package com.paymybuddy.webapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.paymybuddy.webapp.domain.DTO.TransactionBillingDTO;
import com.paymybuddy.webapp.domain.DTO.TransactionMapper;
import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.AdminService;
import com.paymybuddy.webapp.service.IRole;
import com.paymybuddy.webapp.service.UserAccountService;

@Controller
public class AdminController {
	private static final Logger log = LogManager.getLogger(AdminController.class);

	@Autowired
	@Qualifier("roleImpl")
	private IRole role;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionMapper transactionMapper;

	@GetMapping("/home/profil/admin")
	public String getProfilPage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "profil-admin");
			String breadcrumbTransactions = "Historical Transactions";
			model.addAttribute("breadcrumbTransactions", breadcrumbTransactions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		UserDTO user = userAccountService.getUserByEmail(principal.getName());
		String breadcrumbProfilAdmin = "Profil";
		model.addAttribute("breadcrumbProfilAdmin", breadcrumbProfilAdmin);
		model.addAttribute("user", user);
		return "profil-admin";
	}

	@GetMapping("/home/transactions-billing")
	public String getHistoricalTransactionsPage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "transactions-billing");
			this.getransactionsPaginated(1, model, principal);
			String breadcrumbTransactions = "Historical Transactions";
			model.addAttribute("breadcrumbTransactions", breadcrumbTransactions);
		}catch(Exception e) {
			log.error("Failed to retrieve Historical Transactions with fees" + e.getMessage());	
		}
		log.info(" Historical Transactions  page successfull retrieved");
		return "transactions-billing";
	}

	@GetMapping("/admin/page/{pageNber}")
	public String getransactionsPaginated(@PathVariable int pageNber, Model model, Principal principal) {
		List<TransactionBillingDTO> transactions = new ArrayList<TransactionBillingDTO>();
		try {
		int pageSize = 10;
		Page<Transaction> page = adminService.getAllTransactionsWithFees(pageNber, pageSize);
		List<Transaction> listTransactions = page.getContent();
		for (Transaction elem : listTransactions) {
			TransactionBillingDTO transaction = transactionMapper.transactionToTransactionBillingDTO(elem);
			transactions.add(transaction);
		}

		model.addAttribute("currentPage", pageNber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("transactions", transactions);
	} catch (Exception e) {
		log.error("Failed to retrieve transactions" + e.getMessage());
	}
	
	log.info(" Historical transactions successfull retrieved");	
		return "transactions-billing";
	}

	public String isUserOrAdmin(Model model, Principal principal, String view) throws Exception {
		return role.verifRolePrincipalInView(model, principal, view);
	}
}
