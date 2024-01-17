package com.paymybuddy.api.domain.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@Column(name = "id", unique= true)
	private long id;

	@NotNull
	@Column(name = "transaction_date_time ")
	private Date date;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "credit_account_id")
	private Account creditAccount;
	
	@NotNull
	@JoinColumn(name = "credit_user_id")
	private UserApp creditUser;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "beneficiary_count_id")
	private Account beneficiaryAccount;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "beneficiary_user_id")
	private UserApp beneficiaryUser;
	
	@NotNull
	@Column(name = "description")
	private String description;
	
	@Positive
	@NotNull
	@Column(name = "amount")
	private Double amount;
		
	@NotNull
	@Column(name = "transaction_fees")
	private float transactionFees;
}
