package com.paymybuddy.api.service;

<<<<<<< Updated upstream
import java.security.Principal;
=======
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.DTO.UserLoginDTO;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AuthenticationUserDetailService implements UserDetailsService{
	
		@Autowired
		private UserAppService userAccountService;
			
		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			UserLoginDTO userDTO =userAccountService.getUserLoginByEmail(email);
		    User	userdetails= new User(userDTO.getEmail(), userDTO.getPassword(), getGrantedAuthorities(userDTO.getRole()));
		    System.out.println("User details service: "+userdetails); // pour les test 
			return userdetails;
		}

		protected List<GrantedAuthority> getGrantedAuthorities(String role) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));		
			return authorities;
		}
		
		public StringBuffer getUsernamePasswordLoginInfo(Principal user)
		   {
		      StringBuffer usernameInfo = new StringBuffer();
		      
		      UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
		      if(token.isAuthenticated()){
		         User u = (User) token.getPrincipal();
		         usernameInfo.append( u.getUsername());
		      }
		      else{
		         usernameInfo.append("NA");
		      }
		      return usernameInfo;
		   }
}
=======

import org.springframework.stereotype.Service;

/*@Service
public class AuthenticationUserDetailService implements UserDetailsService{
	
		@Autowired
		private UserRepository userRepository;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userRepository.findByUsername(username);
			
			return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
		}

		private List<GrantedAuthority> getGrantedAuthorities(String role) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			return authorities;
		}
	
}*/
>>>>>>> Stashed changes
