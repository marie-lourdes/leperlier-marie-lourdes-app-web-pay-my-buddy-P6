package com.paymybuddy.webapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.paymybuddy.webapp.service.UserAppService;
import com.paymybuddy.webapp.utils.IRole;

@Controller
public class AdminController {
	
	@Autowired
	@Qualifier("roleImpl")
	private IRole role;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private TransactionMapper transactionMapper;

	@GetMapping("/admin/profil")
	public String getProfilPage(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal,  "profil-admin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		UserDTO user = userAppService.getUserByEmail(principal.getName());
		model.addAttribute("user", user);
		return "profil-admin";
	}

	@GetMapping("/admin/transactions-billing")
	public String getHistoricalTransactionsWithFees(Model model, Principal principal) {
		try {
			this.isUserOrAdmin(model, principal,  "transactions-billing");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		this.getransactionsPaginated(1, model, principal);
		return "transactions-billing";
	}

	@GetMapping("/admin/page/{pageNber}")
	public String getransactionsPaginated(@PathVariable int pageNber, Model model, Principal principal) {
		List<TransactionBillingDTO> transactions = new ArrayList<TransactionBillingDTO>();

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
		return "transactions-billing";
	}
	public String isUserOrAdmin(Model model, Principal principal, String view) throws Exception {
		return role.verifRolePrincipalInView(model, principal, view);
	}
}
