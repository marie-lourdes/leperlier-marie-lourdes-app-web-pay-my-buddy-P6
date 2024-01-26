package com.paymybuddy.webapp.utils;

import java.text.DecimalFormat;

public interface IOperation {
	public double add(double balance, double amount); 
	public double withdraw(double balance, double amount) ;
	public boolean isOperationAuthorized (double amount,double userAccountBalance );
	
	default public double formatResultDecimalOperation (Double result) {
		DecimalFormat decimalFormat =new DecimalFormat ("0.00");
		String	resultFormatted=decimalFormat.format(result); 
		return Double.parseDouble(resultFormatted);
		
		
		/* System.out.print(result);
         return Math.round(result * 100.0) / 100.0;*/
       
	}
	
}
