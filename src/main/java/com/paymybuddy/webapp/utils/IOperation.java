package com.paymybuddy.webapp.utils;

public interface IOperation {
	public double add(double balance, double amount); 
	public double withdraw(double balance, double amount) ;
	public boolean isOperationAuthorized (String userId,double amount);
}
