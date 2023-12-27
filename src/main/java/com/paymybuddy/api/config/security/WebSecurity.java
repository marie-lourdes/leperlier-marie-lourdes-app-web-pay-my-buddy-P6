package com.paymybuddy.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//ajoutez les roles
	return http
			.authorizeHttpRequests((requests) ->{ 
				requests.requestMatchers("/","/home").permitAll();
				requests.anyRequest().authenticated();
			})
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
				)
				.logout((logout) -> logout.permitAll()).build();
				
	}
		
}
