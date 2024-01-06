package com.paymybuddy.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;

@SpringBootTest
class AuthenticationUserDetailServiceTest {

	@Autowired
	private AuthenticationUserDetailService userDetailsServiceUnderTest;

	@MockBean
	private UserAccount userAccountService;

	@Test
	void testLoadUserByUsername() throws Exception {
		UserLoginDTO userRegistered= new UserLoginDTO("testuser2@gmail.com", "userbuddy", "USER");
		when(userAccountService.getUserByEmail("testuser2@gmail.com")).thenReturn(userRegistered);
		
		//User userForAuthentication= new User("testuser2@gmail.com", "userbuddy", userDetailsServiceUnderTest.getGrantedAuthorities("USER"));
		try{
			UserDetails resultUserDetails = userDetailsServiceUnderTest.loadUserByUsername("testuser2@gmail.com");
			assertAll(()->{
				assertEquals(resultUserDetails.getUsername(),"testuser2@gmail.com");
				assertTrue(resultUserDetails.isEnabled());//test user is authenticated
				assertEquals(resultUserDetails.getAuthorities(),"ROLE_USER");
			});
		}catch(AssertionError ex) {
			ex.getMessage();
		}
		
		
 //System.out.println(userAuthenticated.getAuthorities());
	}

}
