package com.paymybuddy.webapp.utils;

public class Billing {
	
	public static final double RATE =0.5;
	
	private Billing(){}
	
	public static double calculateFees(double amountTransaction) {
		double result =((RATE *100) /100)*amountTransaction;
		System.out.println("amountransaction"+amountTransaction);
		System.out.println("resultfees"+result);
		return result;
	}

	/*public static  double deduceFeeToUserAccount(double accountBalance,double amountTransaction) {
		double feesCalculated=calculateFees( amountTransaction);	
		return accountBalance -feesCalculated;
	}*/
}
