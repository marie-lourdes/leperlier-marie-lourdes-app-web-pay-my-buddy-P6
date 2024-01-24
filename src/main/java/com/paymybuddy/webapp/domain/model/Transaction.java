package com.paymybuddy.webapp.domain.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transaction")
	private long transactionId;


	@Column(name = "transaction_date_time ")
	private Date date;

	
	@ManyToOne
	@JoinColumn(name = "credit_user_id")
	private UserApp creditUser;

	
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "beneficiary_user_id")
	//@Column(name = "account_beneficiary_user_id")
	private UserApp beneficiaryUser;
	
	@NotNull
	@Column(name = "description")
	private String description;

	@Positive
	@NotNull
	@Column(name = "amount")
	private double amount;

	
	@Column(name = "transaction_fees")
	private double transactionFees;

	public Transaction() {
	}

	public Transaction(Date date, UserApp creditUser, UserApp beneficiaryUser, String description, double amount,
			double transactionFees) {
		this.date = date;
		this.creditUser= creditUser;
		this. beneficiaryUser =  beneficiaryUser;
		this.description = description;
		this.amount = amount;
		this.transactionFees = transactionFees;
	}
	@Override
	public String toString() {
		return "Transaction{" + "date :" + date + ", creditUser:'" + creditUser  + ",  beneficiaryUser:" +  beneficiaryUser
				+ ", description:" +  description + ", amount:" + amount + ", transactionFees:" + transactionFees + '}';
	}
}