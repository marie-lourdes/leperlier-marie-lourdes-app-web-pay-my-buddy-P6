package com.paymybuddy.webapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

@Controller
@ResponseBody
public class UserPrincipalController {
	private String userPrincipal;
	public UserPrincipalController (){}
	
	@GetMapping("/*")
	@ResponseBody
	public void setPrincipal(Principal principal) {
		System.out.println("user principal" + principal.getName());
		this.userPrincipal = principal.getName();

	}
	public String getPrincipal() {
	return userPrincipal;
		
	}

	
}
