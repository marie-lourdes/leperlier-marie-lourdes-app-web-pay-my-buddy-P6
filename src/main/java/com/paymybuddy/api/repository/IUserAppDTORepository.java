package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserAppDTORepository extends JpaRepository<UserApp, String> {
	public UserApp findByEmail(String Email);

}
