package com.paymybuddy.webapp.service;

import java.security.Principal;

import org.springframework.ui.Model;

public interface IRole {
	String verifRolePrincipalInView( Model model, Principal principal, String view) throws Exception;
}
