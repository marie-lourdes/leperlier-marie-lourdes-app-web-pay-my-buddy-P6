package  com.paymybuddy.webapp.domain.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private long id;
	private String email;
	private String firstName;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(long id,String email, String firstName, String lastName) {
		this.id=id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserDTO{" +" id "+ id+"email:" + email + ", firstName:" + firstName + ", lastName:" + lastName + '}';
	}
}
