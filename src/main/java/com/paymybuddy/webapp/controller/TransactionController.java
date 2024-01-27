package com.paymybuddy.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paymybuddy.webapp.domain.DTO.TransactionMapper;
import com.paymybuddy.webapp.service.BankingService;
import com.paymybuddy.webapp.service.UserAppService;


@Controller
public class TransactionController {
	
	@Autowired
	private BankingService bankingService;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private TransactionMapper transactionMapper;
	private String userPrincipal;

}
