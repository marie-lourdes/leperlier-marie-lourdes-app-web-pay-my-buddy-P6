package com.paymybuddy.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.UserApp;


public interface IUserRepository extends JpaRepository<UserApp, Long> {

	 UserApp findByEmail(String email);
	 Optional<UserApp> findById(Long id);
	
	 UserApp findByFirstNameAndLastName(String firstName,String lastName);
	
	/*@Query(value = "select u from UserApp u join u.transactions where u.email=:email")
	public List<Transaction> findUserTransaction(@Param("email")String emailUser);*/

}
