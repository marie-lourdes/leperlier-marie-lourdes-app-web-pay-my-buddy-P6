package com.paymybuddy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping("/sign-up")
	public String getHomePage() {
		return "sign-up";
	}
	
	@GetMapping("/login")//
	public String  getLoginPage() {
		return "login";
	}
	
	@PostMapping("/login-form")//traitement formulaire
	public ModelAndView loginForm() {
		return new ModelAndView("redirect:/");	
	}

}
