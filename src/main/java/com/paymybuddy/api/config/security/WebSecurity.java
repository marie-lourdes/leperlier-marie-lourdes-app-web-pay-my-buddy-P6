package com.paymybuddy.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//ajoutez les roles
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/*").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
					.loginPage("/home")
					.permitAll()
				)
				.logout((logout) -> logout.permitAll());
		
			

		return http.build();
	}
	
	
}
