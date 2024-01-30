package com.paymybuddy.webapp.utils;

import java.security.Principal;

import org.springframework.ui.Model;

import com.paymybuddy.webapp.service.UserAppService;

public interface IRole {
	String verifRolePrincipalInView( Model model, Principal principal, String view) throws Exception;
}
