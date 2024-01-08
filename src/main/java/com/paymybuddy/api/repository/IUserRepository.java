package com.paymybuddy.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserRepository extends JpaRepository<UserApp, String> {
	
	/*@Query("SELECT email FROM paymybuddy_test.user_app WHERE user_app.email = :email")
	public UserApp findByEmail(@Param("email")String email);*/
	public Optional<UserApp> findByEmail(String email);
	
	//public Iterable<UserApp >findByRoles(String name);
}
