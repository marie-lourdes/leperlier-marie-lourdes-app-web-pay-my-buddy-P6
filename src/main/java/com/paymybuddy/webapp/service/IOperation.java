package com.paymybuddy.webapp.service;

public interface IOperation {
	 double add(double balance, double amount); 
	 double withdraw(double balance, double amount) ;
	 boolean isOperationAuthorized (double amount,double userAccountBalance );
	

      
	
}
