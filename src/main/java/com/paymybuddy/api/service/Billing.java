package com.paymybuddy.api.service;

import org.springframework.stereotype.Component;

@Component
public class Billing {
	
	private static final double RATE = 0.5 ;
	
	private Billing(){}
	
	public static void calculateFees(double amount) {
		
	}

}
