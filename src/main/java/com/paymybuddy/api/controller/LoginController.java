package com.paymybuddy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@GetMapping("/home")//
	public String  getHomePage() {
		return "home";
	}
}
