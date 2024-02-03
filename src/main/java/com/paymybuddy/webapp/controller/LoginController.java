package com.paymybuddy.webapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.webapp.service.IRole;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("roleImpl")
	private IRole role;

	@GetMapping("/login") //
	public String getLoginPage(Model model) {

		return "login";
	}

	@GetMapping("/logout") //
	public String getLogoutPage(Model model, java.security.Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "logout");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		String breadcrumbLogOff = "Log Off";
		model.addAttribute("breadcrumbLogOff", breadcrumbLogOff);
		return "logout";

	}

	@GetMapping("/logout-success")
	public String getLogoutSuccessPage() {
		return "logout-success";
	}

	public String isUserOrAdmin(Model model, Principal principal, String view) throws Exception {
		return role.verifRolePrincipalInView(model, principal, view);
	}

}
