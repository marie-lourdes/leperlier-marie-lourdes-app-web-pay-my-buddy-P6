package com.paymybuddy.webapp.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", unique = true)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserApp user;

	@NotNull
	@Positive
	@Column(name = "balance")
	private double balance;

	/*@Column(name = "type")
	private String type;*/

	@Column(name = "creation")
	private Date creation;
	
	@Override
	public String toString() {
		return "Account{" + "id:" + id + ",user:'" + user + ",balance:" + balance
				+ ", creation:" + creation+ '}';
	}
	
}
