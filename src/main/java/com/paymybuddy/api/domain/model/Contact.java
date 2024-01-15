package com.paymybuddy.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact{
	private final String REGEX_P = "^(.+)@(\\S+)$";
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id_contact")
	private String idContact;
	
	@NotNull
	@Pattern(regexp = REGEX_P)
	@Column(name = "user_id")
	private String userId;


	/*@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserApp user;*/

	@Override
	public String toString() {
		return "UserApp{" + "idContact:" + idContact+"userId: " +userId+   '}';
	}
}