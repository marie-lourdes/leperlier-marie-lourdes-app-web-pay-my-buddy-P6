package com.paymybuddy.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;

@Transactional
public interface IUserRepository extends JpaRepository<UserApp, String> {

	public UserApp findByEmail(String email);
	
	public UserApp findByFirstNameAndLastName(String firstName,String lastName);
	
	@Query(value = "select u from UserApp u join u.transactions where u.email=:email")
	public List<Transaction> findUserTransaction(@Param("email")String emailUser);
}
