package com.paymybuddy.webapp.utils;

import java.security.Principal;

import org.springframework.ui.Model;

public interface IRole {
	String verifRolePrincipalInView( Model model, Principal principal, String view) throws Exception;
	
}
