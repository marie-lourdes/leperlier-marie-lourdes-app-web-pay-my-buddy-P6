package com.paymybuddy.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@Autowired
	private UserAccountController userAccountController ; 

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
			userAccountController.isUserOrAdmin(model, principal, "logout");
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
	/*
	 * @PostMapping("/login")//traitement formulaire et generation token avec JWT
	 * public ModelAndView loginForm() { return new ModelAndView("redirect:/"); }
	 */

	
	
}
