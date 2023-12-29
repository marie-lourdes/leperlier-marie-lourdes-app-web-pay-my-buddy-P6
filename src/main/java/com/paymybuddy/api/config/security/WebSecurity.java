package com.paymybuddy.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// ajoutez les roles
		return http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/sign-up").permitAll();
			requests.anyRequest().authenticated();
		})

				.formLogin((form) -> form.loginPage("/login").permitAll().loginProcessingUrl("/login-form"))
				.logout((logout) -> logout.permitAll()).build();

	}
}
