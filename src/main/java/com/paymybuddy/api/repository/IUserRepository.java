package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserRepository extends JpaRepository<UserApp, String> {

	public UserApp findByEmail(String email);
	
	public UserApp findByFirstName(String firstName);
}
