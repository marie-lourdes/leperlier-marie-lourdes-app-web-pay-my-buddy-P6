package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.api.domain.model.UserApp;

@Transactional
public interface IUserRepository extends JpaRepository<UserApp, String> {

	public UserApp findByEmail(String email);
	
	public UserApp findByFirstName(String firstName);
}
