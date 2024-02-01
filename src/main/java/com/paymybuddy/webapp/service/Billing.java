package com.paymybuddy.webapp.service;

public class Billing {
	
	public static final double RATE =0.5;
	
	private Billing(){}
	
	public static double calculateFees(double amountTransaction) {
		return RATE*(amountTransaction/100);
	}
}
