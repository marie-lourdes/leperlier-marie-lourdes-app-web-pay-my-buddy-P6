package com.paymybuddy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login") //
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/logout") //
	public String getLogoutPage() {
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
