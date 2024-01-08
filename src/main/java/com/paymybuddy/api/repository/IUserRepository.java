package com.paymybuddy.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.api.domain.model.UserApp;

public interface IUserRepository extends JpaRepository<UserApp, String> {
	
	/*@Query("SELECT email FROM paymybuddy_test.user_app WHERE user_app.email = :email")
	public UserApp findByEmail(@Param("email")String email);*/
	public Optional<UserApp> findByEmail(String email);
	
	/*@Query("UPDATE paymybuddy_test.user_app u SET u.user_contact_id= :emailContact WHERE u.email =:email")	
	 public void addContact(@Param(value="emailContact") String emailContact, @Param(value="email")String email );*/
	
	 @Query("select u from UserApp u where u.email = ?1")
	public  UserApp findByEmailAddress(String email);
	
	//public Iterable<UserApp >findByRoles(String name);
}
