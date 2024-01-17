package com.paymybuddy.api.service.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.model.Transaction;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.ITransactionRepository;
import com.paymybuddy.api.service.UserAppService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TransactionService {
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	UserAppService userAppService ;
	
	public List<Transaction> getAllTransactions(){
		return transactionRepository.findAll();
	}
 
	public List<Transaction> getTransactionsByCreditUser(UserApp creditUser){
		return  transactionRepository.findByCreditUser(creditUser);
	}
	
	public Transaction createTransaction( String nameBeneficiary,String creditUserId){
	
		Stream<UserApp> contactUser=userAppService.findUserContacts(creditUserId)
				.stream()
				.filter((elem)->{ return elem.getFirstName().equals(nameBeneficiary);
		});
		List<UserApp>	usercontact= contactUser.collect(Collectors.toList());
		usercontact.get(0);
		//Stream<Object> resultMapContactUser= contactUser.map(elem->elem);
	System.out.println("filter contact firstname"+usercontact);
	return null;
	}
	
}
