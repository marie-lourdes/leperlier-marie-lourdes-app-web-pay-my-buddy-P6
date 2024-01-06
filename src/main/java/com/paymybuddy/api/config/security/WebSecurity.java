package com.paymybuddy.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;

import com.paymybuddy.api.service.AuthenticationUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Autowired
	private AuthenticationUserDetailService authenticationService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/transactions-billing").hasRole("ADMIN");
			requests.requestMatchers("/sign-up").permitAll();
			requests.requestMatchers("/logout-success").permitAll();
			requests.requestMatchers("/css/**").permitAll();
			requests.anyRequest().authenticated();

		}).rememberMe((remember) -> {
			remember.rememberMeServices(rememberMeServices(authenticationService));
			remember.useSecureCookie(true);
		}).formLogin(form -> form.loginPage("/login").permitAll())
		.logout((logout) -> {
			logout.logoutSuccessUrl("/logout-success");
			logout.deleteCookies("JSESSIONID");
		});

		return http.build();

	}

	@Bean
	RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(
				"key89745&secretet&trestreslongetunique&", userDetailsService, encodingAlgorithm);
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.SHA256);
		return rememberMe;
	}

}
