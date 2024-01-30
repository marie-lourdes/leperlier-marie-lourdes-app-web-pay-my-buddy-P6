package com.paymybuddy.webapp.utils;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.paymybuddy.webapp.service.UserAppService;

import lombok.Data;

@Data
@Component(value="principalRole")
public class RoleImpl  implements  IRole{
	@Autowired
	private UserAppService userAppService;
	private boolean isUser;
	private boolean isAdmin;
	private String userRole;
	
	@Override
	public String verifRolePrincipalInView( Model model, Principal principal, String view) throws Exception{
    //UserAppService userAppService= new UserAppService();
	    userRole = userAppService.getUserLoginByEmail(principal.getName()).getRole();
		System.out.println("userRole"+userRole);
		isUser = userRole.equals("USER");
		isAdmin = userRole.equals("ADMIN");
		model.addAttribute("isUser", isUser);
		model.addAttribute("isAdmin", isAdmin);
		return view;
	}
}
