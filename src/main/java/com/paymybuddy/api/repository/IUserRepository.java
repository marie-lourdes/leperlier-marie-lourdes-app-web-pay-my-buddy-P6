package com.paymybuddy.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.api.domain.model.Contact;
import com.paymybuddy.api.domain.model.UserApp;

public interface IUserRepository extends JpaRepository<UserApp, String> {
	
	/*@Query("SELECT email FROM paymybuddy_test.user_app WHERE user_app.email = :email")
	public UserApp findByEmail(@Param("email")String email);*/
	
	public Optional<UserApp> findByEmail(String email);
	@Transactional
	@Modifying//annotation pour indiquer a JPA qu il s agit d une requete PUT
	@Query("update UserApp u  set u.userContactId=?1 where u.email =?2")	
	 public void setContacts(List<Contact> contacts, String email );
	
	 @Query("select u from Contact u where u.email = ?1")
	public  Contact  findByEmailAddress(String email);
	
	
}
