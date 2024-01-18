package  com.paymybuddy.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "user_app")
public class UserApp {
	private final String REGEX_P = "^(.+)@(\\S+)[.](\\S+)$";

	@Id
	@NotNull
	@Pattern(regexp = REGEX_P)
	@Column(name = "user_id", unique= true)
	private String email;

	@NotNull
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role = "USER";
	
	/*@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Contact> contacts;*/
	

	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					}	
			)
	@JoinTable(
			name = "contacts",
			joinColumns = @JoinColumn(name =  "user_id"), 
			inverseJoinColumns = @JoinColumn(name =  "id_contact")
			)
	List<UserApp> contacts = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Account> buddyAccount;
	
	@OneToMany(mappedBy = "creditUser", fetch = FetchType.EAGER)
	private List<Transaction> transactions= new ArrayList<>();
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	public void addContact(UserApp contact) {
		this.contacts.add(contact);
		
	}
	
	@Override
	public String toString() {
		return "UserApp{" + "email:" + email + ", first name:'" + firstName + '\'' + ", last name:" + lastName
				+ ", password:" + password + ", contacts:" + contacts + ", role:" + role + '}';
	}
}
