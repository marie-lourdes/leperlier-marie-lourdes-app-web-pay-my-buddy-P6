package  com.paymybuddy.webapp.domain.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private String role;

	public UserDTO() {

	}

	public UserDTO(long id,String email, String firstName, String lastName, String role) {
		this.id=id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role=role;
	}

	@Override
	public String toString() {
		return "UserDTO{" +" id "+ id+"email:" + email + ", firstName:" + firstName + ", lastName:" + lastName + "role"+role+'}';
	}
}
