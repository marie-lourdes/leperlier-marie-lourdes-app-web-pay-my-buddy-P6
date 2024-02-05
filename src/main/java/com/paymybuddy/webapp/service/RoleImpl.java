package com.paymybuddy.webapp.service;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.paymybuddy.webapp.controller.UserPaymentController;

import lombok.Data;

@Data
@Component(value = "roleImpl")
public class RoleImpl implements IRole {
	private static final Logger log = LogManager.getLogger(RoleImpl.class);
	@Autowired
	private UserAccountService userAppService;
	private boolean isUser;
	private boolean isAdmin;
	private String userRole;

	@Override
	public String verifRolePrincipalInView(Model model, Principal principal, String view) {
		// UserAppService userAppService= new UserAppService();
		try {
			userRole = userAppService.getUserLoginByEmail(principal.getName()).getRole();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		isUser = userRole.equals("USER");
		isAdmin = userRole.equals("ADMIN");
		model.addAttribute("isUser", isUser);
		model.addAttribute("isAdmin", isAdmin);
		return view;
	}
}
