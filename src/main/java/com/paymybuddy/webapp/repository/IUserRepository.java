package com.paymybuddy.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.UserApp;

import jakarta.transaction.Transactional;


public interface IUserRepository extends JpaRepository<UserApp, Long> {

	public UserApp findByEmail(String email);
	public Optional<UserApp> findById(Long id);
	
	public UserApp findByFirstNameAndLastName(String firstName,String lastName);
	
	/*@Query(value = "select u from UserApp u join u.transactions where u.email=:email")
	public List<Transaction> findUserTransaction(@Param("email")String emailUser);*/

}
