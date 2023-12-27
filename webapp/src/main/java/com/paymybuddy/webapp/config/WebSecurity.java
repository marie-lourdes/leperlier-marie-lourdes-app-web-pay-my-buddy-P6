package com.paymybuddy.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*@Configuration
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
		
}*/
