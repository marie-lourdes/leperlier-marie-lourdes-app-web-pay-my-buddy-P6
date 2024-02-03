package com.paymybuddy.webapp.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.webapp.domain.DTO.UserDTO;
import com.paymybuddy.webapp.utils.IFormat;

class BankingServiceTest {
	@Autowired
	private BankingService bankingServiceUnderTest;
	
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
	
	/*@BeforeAll
	static void setUp() {
		 userTest2 = new UserDTO();
		 userTest2.setEmail("userTest@email.com");
		 userTest2.setFirstName("first);
	}*/

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
