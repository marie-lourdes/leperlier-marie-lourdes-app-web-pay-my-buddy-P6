package com.paymybuddy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "sign-up";
	}
	
	@GetMapping("/login")//
	public String  getLoginPage() {
		return "login";
	}
	
	/*@PostMapping("/login")//traitement formulaire et generation token avec JWT
	public ModelAndView loginForm() {
		return new ModelAndView("redirect:/");	
	}*/
	
	@GetMapping("/account/home")
	public String  getHomePage() {
		return "home";
	}
	
	@GetMapping("/error")
	public String  getPage404() {	   
		return "error";
	}
}
