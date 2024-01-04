package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Role;

public interface IRoleRepository extends JpaRepository<Role,Integer> {
	public Role findByName(String name);

}
