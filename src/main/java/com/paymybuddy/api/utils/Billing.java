package com.paymybuddy.api.utils;

import org.springframework.stereotype.Component;


public class Billing {
	
	private static final double RATE = 0.5 ;
	
	private Billing(){}
	
	public static void calculateFees(double amountTransation) {
		double result = 0;
		Integer percentFee= (int) (RATE *100);
		result =percentFee/amountTransation;
	}
	/*public static void calculateFees(double amountTransation) {
		(RATE *100)*amount/amount
	}*/
}
