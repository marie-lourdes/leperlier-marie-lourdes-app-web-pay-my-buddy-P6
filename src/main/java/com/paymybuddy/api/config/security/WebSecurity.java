package com.paymybuddy.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity  {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// ajoutez les roles
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/sign-up").permitAll();
			requests.requestMatchers("/transactions").hasRole("ADMIN");
			requests.requestMatchers("/login").rememberMe();
			requests.anyRequest().authenticated();
			
		}).formLogin((form) -> form.loginPage("/login").permitAll().defaultSuccessUrl("/account/home"))
				.logout((logout) -> logout.permitAll());
				
		
		return http.build();
	}
}
