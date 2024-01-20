package com.paymybuddy.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.UserApp;

@Transactional
public interface IUserRepository extends JpaRepository<UserApp, String> {

	public UserApp findByEmail(String email);
	
	public UserApp findByFirstNameAndLastName(String firstName,String lastName);
}