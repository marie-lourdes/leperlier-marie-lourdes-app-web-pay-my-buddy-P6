package com.paymybuddy.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.webapp.domain.model.UserApp;


public interface IUserRepository extends JpaRepository<UserApp, Long> {
	 UserApp findByEmail(String email);
	 Optional<UserApp> findById(Long id);
}
