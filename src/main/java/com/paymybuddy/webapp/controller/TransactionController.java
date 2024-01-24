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
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserAppService;

import jakarta.validation.Valid;
import lombok.Data;


@Controller
public class TransactionController {
	
	@Autowired
	private BankingService bankingService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserAppService userAppService;

	private String userPrincipal;


	public String getUserPrincipal() {
		return userPrincipal;
			
		}
}
