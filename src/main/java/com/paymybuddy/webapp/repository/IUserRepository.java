package com.paymybuddy.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.UserApp;

import jakarta.transaction.Transactional;

@Transactional
public interface IUserRepository extends JpaRepository<UserApp, Long> {

	public UserApp findByEmail(String email);
	
	public UserApp findByFirstNameAndLastName(String firstName,String lastName);
	
	/*@Query(value = "select u from UserApp u join u.transactions where u.email=:email")
	public List<Transaction> findUserTransaction(@Param("email")String emailUser);*/

}
