package com.paymybuddy.api.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public abstract class Account {
	@Id
	@NotNull
	private String id;

	@NotNull
	@Column(name = "user_id")
	private String firstName;

	@NotNull
	@Column(name = "solde")
	private double solde;
	
	@NotNull
	@Column(name = "type")
	private String type;
	
}
