package com.paymybuddy.api.config.security;

<<<<<<< Updated upstream
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
			requests.requestMatchers("/error-400").permitAll();
			requests.requestMatchers(HttpMethod.POST,"/sign-up-form").permitAll();
			requests.requestMatchers("/logout-success").permitAll();
			requests.requestMatchers("/css/**").permitAll();
			requests.anyRequest().authenticated();

		})
		.rememberMe((remember) -> {
			remember.rememberMeServices(rememberMeServices(authenticationService));
			remember.useSecureCookie(true);
		})
	//	.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//empeche la redirection sur la page demandé apres authentification, session stateless
		.formLogin(form -> form.loginPage("/login").permitAll())
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
=======
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
>>>>>>> Stashed changes
