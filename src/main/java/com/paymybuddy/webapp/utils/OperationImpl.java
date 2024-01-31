package com.paymybuddy.webapp.utils;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component(value="operationFormatImpl")
public class OperationImpl implements IOperation{
	private double resultFormattedParsed;
	
	@Override
	public double add(double balance, double amount) {
		return balance + amount;
	}

	@Override
	public double withdraw(double balance, double amount) {
		return balance - amount;
	}

	@Override
	public boolean isOperationAuthorized(double amount, double userAccountBalance) {
		boolean isAuthorized = false;
		if (userAccountBalance <= 0 || amount <= 0) {
			isAuthorized = true;
		}
		return isAuthorized;
	}
	
}
