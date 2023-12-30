package com.paymybuddy.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.domain.model.UserApp;
import com.paymybuddy.api.repository.IUserAppDTORepository;

/*@Service
public class AuthenticationUserDetailService implements UserDetailsService{
	
		@Autowired
		private IUserAppDTORepository userAppDTORepository;

		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			UserApp userDTO = userAppDTORepository.findByEmail(email);
			
			return new User(userDTO.getUsername(), userDTO.getPassword(), getGrantedAuthorities(userDTO.getRole()));
		}

		private List<GrantedAuthority> getGrantedAuthorities(String role) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			return authorities;
		}
	
}*/
