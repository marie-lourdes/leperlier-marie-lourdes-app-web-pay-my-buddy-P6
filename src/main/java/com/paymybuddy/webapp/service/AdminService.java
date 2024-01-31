package com.paymybuddy.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.Transaction;

@Transactional
@Service
public class AdminService {

	@Autowired
	private TransactionService transactionService;
	
	public Page<Transaction> getAllTransactionsWithFees(int pageNber, int pageSize) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(pageNber - 1, pageSize,sort);
		return transactionService.findAllTransactionsPaginated( pageable);
	}
}
