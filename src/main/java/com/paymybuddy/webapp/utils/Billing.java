package com.paymybuddy.webapp.utils;

public class Billing {
	
	private static final double RATE = 0.5 ;
	
	private Billing(){}
	
	public static double calculateFees(double amountTransation) {
		double result = 0.0;
		Integer percentFee= (int) (RATE *100);
		result =percentFee/amountTransation;
		return result;
	}
	/*public static void calculateFees(double amountTransation) {
		(RATE *100)*amount/amount
	}*/
}
