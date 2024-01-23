package com.paymybuddy.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.controller.UserPrincipalController;

import lombok.Data;


@Component
public  class UserPrincipalSingleton {
	private UserPrincipalController userPrincipalController = new UserPrincipalController() ;
	private String userPrincipal;
	private UserPrincipalSingleton(){
		
	}
	
	public String getUserPrincipal() {
		return this.userPrincipal=userPrincipalController.getPrincipal();
	}
}
