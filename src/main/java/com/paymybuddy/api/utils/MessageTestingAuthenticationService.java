package com.paymybuddy.api.utils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.paymybuddy.api.config.security.Authentication;

@Component
public class MessageTestingAuthenticationService  implements MessageService{
	private String message;
	@PreAuthorize("authenticated")//check the expression authenticated with the method getMessage()
    public String getMessage() {
		AuthenticationManager authentication =  (AuthenticationManager) SecurityContextHolder.getContext().getAuthentication();
      return  "Hello" + authentication ;
   }

}
