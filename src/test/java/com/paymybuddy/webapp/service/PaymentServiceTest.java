package com.paymybuddy.webapp.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.utils.IFormat;

@SpringBootTest
class PaymentServiceTest {
	@Autowired
	private PaymentService paymentServiceTest;
	
	@MockBean
	@Qualifier("operationImpl")
	private IOperation operation;

	@MockBean
	@Qualifier("formatterImpl")
	private IFormat formatter;

	@MockBean
	private PaymentStrategy payment;

	@MockBean
	private UserAppService userAppService;

	@MockBean
	private AccountService accountService;

	@MockBean
	private TransactionService transactionService;
	
	private static UserDTO userTest2 ;
	
	@BeforeAll
	static void setUp() {
		 userTest2 = new UserDTO();
		 userTest2.setId(1);
		 userTest2.setEmail("userTest@email.com");
		 userTest2.setFirstName("Firstnameusertest");
		 userTest2.setFirstName("Lastnameusertest");
		 userTest2.setRole("USER");
		 
		 userTest2 = new UserDTO();
		 userTest2.setId(1);
		 userTest2.setEmail("userTest@email.com");
		 userTest2.setFirstName("Firstnameusertest");
		 userTest2.setFirstName("Lastnameusertest");
		 userTest2.setRole("USER");
	}

	@Test
	void testPayToContact() {
		when(userAppService.getUserByEmail("userTest@email.com")).thenReturn(userTest2 );
		when( accountService.findBuddyAccountByUser("userTest@email.com").getBalance()).thenReturn(380.00);
		when( accountService.findBuddyAccountByUser("userTest@email.com").getBalance()).thenReturn(380.00);
		 accountService.findBuddyAccountByUser("userBeneficiaryTest@email.com");
		fail("Not yet implemented");
	}

}
