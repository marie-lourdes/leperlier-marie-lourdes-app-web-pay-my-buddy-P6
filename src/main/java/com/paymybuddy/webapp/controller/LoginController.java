package com.paymybuddy.webapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.webapp.domain.DTO.UserLoginDTO;
import com.paymybuddy.webapp.domain.model.UserApp;
import com.paymybuddy.webapp.service.UserAppService;
import com.paymybuddy.webapp.utils.IRole;

import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("roleImpl")
	private IRole role;
	
	@Autowired
	private UserAppService userAppService;

	@GetMapping("/login") //
	public String getLoginPage(Model model ) {
		UserLoginDTO userCreated = new UserLoginDTO();
		model.addAttribute("user", userCreated);
		return "login";
	}
	
	@PostMapping("/login") //
	public ModelAndView saveLogin(@Valid @ModelAttribute UserApp user){
		try {
		userAppService.createUser(user);
		return new ModelAndView("redirect:/home");
	} catch (IllegalArgumentException e) {
		System.out.println(e.getMessage());
		// response.setIntHeader("status",400);
		return new ModelAndView("redirect:/error");
	} catch (NullPointerException e) {
		System.out.println(e.getMessage());
		// response.setIntHeader("status",400);
		return new ModelAndView("redirect:/error");
	}
	
	}
	
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
	
}
