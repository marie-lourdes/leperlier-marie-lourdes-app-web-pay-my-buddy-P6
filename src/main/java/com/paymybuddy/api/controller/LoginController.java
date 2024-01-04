package com.paymybuddy.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "sign-up";
	}

	@GetMapping("/login") //
	public String getLoginPage() {
		return "login";
	}

	/*
	 * @PostMapping("/login")//traitement formulaire et generation token avec JWT
	 * public ModelAndView loginForm() { return new ModelAndView("redirect:/"); }
	 */

	@GetMapping("/account/home")
	public String getHomePage() {
		return "home";
	}


	@GetMapping("/transactions")
	public String getTransactionPage() {
		return"home";
	}
	
	/*@GetMapping("/error")
	public String getPage404() {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-403";
			}

		}

		return "error";
	}*/

	@GetMapping("/error")
	public String getPage404() {
		return "error";
	}
	
}
