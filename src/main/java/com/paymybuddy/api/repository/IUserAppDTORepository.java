package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserAppDTORepository extends JpaRepository<UserApp, String> {
	/*@Query("SELECT email FROM paymybuddy_test.user_app WHERE user_app.email = :email")
	public UserApp findByEmail(@Param("email")String email);*/
	public UserApp findByEmail(String email);
	

}