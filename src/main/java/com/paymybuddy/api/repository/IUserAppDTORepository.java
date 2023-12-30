package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserAppDTORepository extends JpaRepository<UserApp, String> {
	public UserApp findByEmail(String Email);
	
	@Query("\r\n"
			+ "      SELECT role.role_name \r\n"
			+ "        FROM user_app\r\n"
			+ "        JOIN user_app_role ON(user_app.email=user_app_role.user_id )\r\n"
			+ "        JOIN role ON(role.id=user_app_role.role_id )\r\n"
			+ "        WHERE user_app.id =:email  ")
	public String findByRole( @Param("email") String email);


}
