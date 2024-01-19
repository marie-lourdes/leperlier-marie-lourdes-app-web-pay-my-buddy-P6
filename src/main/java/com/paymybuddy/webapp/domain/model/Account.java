package  com.paymybuddy.webapp.domain.model;

import java.util.Date;

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
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique= true)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserApp user;

	@NotNull
	@Positive
	@Column(name = "balance")
	private double balance;

	@Column(name = "type")
	private String type;

	@Column(name = "creation")
	private Date creation;
}
