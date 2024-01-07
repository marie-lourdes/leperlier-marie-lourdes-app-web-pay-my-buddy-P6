package com.paymybuddy.api.utils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import com.paymybuddy.api.config.security.Authentication;

public class MessageTestingAuthenticationService  implements MessageService{
	@PreAuthorize("authenticated")//check the expression authenticated with the method getMessage()
    public String getMessage() {
       Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
      return  "Hello" + authentication ;
   }

}
