package com.paymybuddy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.service.UserAccount;

@Controller
public class LoginController {
	@Autowired
	private UserAccount userAccount ; ;
	
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
