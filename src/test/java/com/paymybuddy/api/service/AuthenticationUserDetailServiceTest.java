package com.paymybuddy.api.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.api.utils.MessageTestingAuthenticationService;


@ContextConfiguration
class AuthenticationUserDetailServiceTest {

	@Autowired
	MessageTestingAuthenticationService messageService;
	
	@Autowired
	private AuthenticationUserDetailService userDetailsServiceUnderTest;

	@MockBean
	private UserAccount userAccountService;
	
	@BeforeEach
	public void setup() {
		messageService=	new MessageTestingAuthenticationService();
	}
	
	/*@BeforeEach
	public void setup() {
		this.rest = WebTestClient
			.bindToApplicationContext(this.context)
			// add Spring Security test Support
			.apply((MockServerConfigurer) springSecurity())
			.build();
	}*/
	
	/*@Test
	public void messageWhenNotAuthenticated() throws Exception {
		this.rest
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isUnauthorized();
	}*/
	@Test
	@WithMockUser(username="ghjgj@zehjbd.com", password="jnekf")
	  void getMessageWithUserDetailsService() {
	
		String message = messageService.getMessage();
		System.out.println(message);
	}
	
	
	
/*	@Test
	void testLoadUserByUsername() throws Exception {
		UserLoginDTO userRegistered= new UserLoginDTO("testuser2@gmail.com", "userbuddy", "USER");
		when(userAccountService.getUserByEmail("testuser2@gmail.com")).thenReturn(userRegistered);
		
		//User userForAuthentication= new User("testuser2@gmail.com", "userbuddy", userDetailsServiceUnderTest.getGrantedAuthorities("USER"));
		//test pas fiable
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
	}*/

}
