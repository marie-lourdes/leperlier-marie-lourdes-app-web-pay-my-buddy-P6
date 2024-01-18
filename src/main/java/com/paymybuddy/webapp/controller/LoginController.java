package com.paymybuddy.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	/*@Autowired
	private UserAccount userAccount ; */

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
