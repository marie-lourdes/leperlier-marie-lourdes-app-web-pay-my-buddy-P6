package com.paymybuddy.webapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.webapp.utils.IRole;

@Controller
public class LoginController {
	@Autowired
	private IRole role;
	
	@GetMapping("/login") //
	public String getLoginPage() {
		return "login";
	}
	
	/*@PostMapping("/login") //
	public UserLoginDTO saveLogin(@ModelAttribute UserLoginDTO user){
		UserLoginDTO current =userAccount.getUserLoginByEmail(user.getEmail());
		return current;
	}*/
	
	@GetMapping("/logout") //
	public String getLogoutPage(Model model, java.security.Principal principal) {
		try {
			this.isUserOrAdmin(model, principal, "logout");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return "logout";
		
	}

	@GetMapping("/logout-success")
	public String getLogoutSuccessPage() {
		return "logout-success";
	}
	
	public String isUserOrAdmin(Model model, Principal principal, String view) throws Exception {
		return role.verifRolePrincipalInView(model, principal, view);
	}
	/*
	 * @PostMapping("/login")//traitement formulaire et generation token avec JWT
	 * public ModelAndView loginForm() { return new ModelAndView("redirect:/"); }
	 */

	
	
}
