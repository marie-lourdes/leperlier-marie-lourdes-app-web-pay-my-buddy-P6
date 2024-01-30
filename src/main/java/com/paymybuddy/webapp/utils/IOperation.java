package com.paymybuddy.webapp.utils;

public interface IOperation {
	 double add(double balance, double amount); 
	 double withdraw(double balance, double amount) ;
	 boolean isOperationAuthorized (double amount,double userAccountBalance );
	
	default double formatResultDecimalOperation (Double result) {
         return Math.round(result * 100.00) / 100.00;   
	}
	
}
