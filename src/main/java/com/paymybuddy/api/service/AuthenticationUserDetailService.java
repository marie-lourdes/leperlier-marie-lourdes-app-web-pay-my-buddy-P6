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

import com.paymybuddy.api.domain.DTO.UserLoginDTO;
import com.paymybuddy.api.domain.model.Role;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AuthenticationUserDetailService implements UserDetailsService{
	
		@Autowired
		private UserAccount userAccountService;
			
		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			UserLoginDTO userDTO =userAccountService.getUserByEmail(email);
			
			return new User(userDTO.getEmail(), userDTO.getPassword(), getGrantedAuthorities(userDTO.getRoles()));
		}

		private List<GrantedAuthority> getGrantedAuthorities(List<Role> role) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			role.forEach(roleUser->authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));		
			return authorities;
		}
	
}
