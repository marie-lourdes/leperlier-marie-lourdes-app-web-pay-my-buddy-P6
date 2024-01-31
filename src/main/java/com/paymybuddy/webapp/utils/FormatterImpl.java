package com.paymybuddy.webapp.utils;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component(value="formatterImpl")
public class FormatterImpl implements IFormat{

	@Override
	public double formatResultDecimalOperation (double result) {	
    return Math.round(result * 100.00) / 100.00;  
		
	}
}
