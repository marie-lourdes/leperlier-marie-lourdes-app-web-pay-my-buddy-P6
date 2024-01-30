package com.paymybuddy.webapp.utils;

import java.security.Principal;

import org.springframework.ui.Model;

import com.paymybuddy.webapp.service.UserAppService;

public interface IRole {
	default String verifRolePrincipalInView( Model model, Principal principal, String view) throws Exception{
		    UserAppService userAppService= new UserAppService();
			String userRole = userAppService.getUserLoginByEmail(principal.getName()).getRole();
			boolean isUser = userRole.equals("USER");
			boolean isAdmin = userRole.equals("ADMIN");
			model.addAttribute("isUser", isUser);
			model.addAttribute("isAdmin", isAdmin);
			return view;
		}
	
	
}
