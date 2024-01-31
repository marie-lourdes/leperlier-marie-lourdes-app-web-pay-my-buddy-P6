package  com.paymybuddy.webapp.domain.DTO;

import org.springframework.stereotype.Component;

import com.paymybuddy.webapp.domain.model.UserApp;

@Component
public class UserMapper {
	public UserLoginDTO userToUserLoginDTO(UserApp user) {
		String email = user.getEmail();
		String password = user.getPassword();
	    String role = user.getRole();
		
		return new UserLoginDTO (email,password,role);
	}
	
	public UserDTO userToUserDTO(UserApp user) {
		long id= user.getId();
		String email = user.getEmail();
	    String firstName = user.getFirstName();	
	    String lastName = user.getLastName();	
	     
		return new UserDTO (id,email, firstName,lastName);
	}
}