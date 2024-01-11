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
	protected String id;

	@NotNull
	@Column(name = "user_id")
	protected String userId;

	@NotNull
	@Column(name = "solde")
	protected double solde;
	
	@NotNull
	@Column(name = "type")
	protected String type;
	
	@Override
	public String toString() {
		return "Account{" + "email:" + id+ ", userid:'" + userId + '\'' + ", solde:" + solde + ", type:" + type + '}';
	}
}
