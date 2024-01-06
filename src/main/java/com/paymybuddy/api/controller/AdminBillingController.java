package com.paymybuddy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminBillingController {

	@GetMapping("/transactions-billing")
	public String getTransactionHistoryPage() {
		return "transactions-history";
	}
	
}
