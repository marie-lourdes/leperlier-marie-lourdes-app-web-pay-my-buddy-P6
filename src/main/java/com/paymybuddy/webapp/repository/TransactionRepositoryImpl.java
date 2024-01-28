package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;

public class TransactionRepositoryImpl implements ITransactionRepository{
	

	@Override
	public Page<Transaction>  findTransactionsPaginated(int pageNber, int pageSize) {
	 Pageable pageable = PageRequest.of(pageNber - 1, pageSize);
	 return findAll(pageable);
	}
	
	@Override
	public List<Transaction> findByCreditUser(UserApp creditUser) {
		return findByCreditUser(creditUser);
	}
}
