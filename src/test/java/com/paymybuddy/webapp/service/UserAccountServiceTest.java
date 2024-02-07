package com.paymybuddy.webapp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webapp.domain.model.Account;
import com.paymybuddy.webapp.domain.model.BankingAccount;
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;

@SpringBootTest
class UserAccountServiceTest {
	@Autowired
	private UserAccountService userAccountServiceUnderTest;

	private UserApp userCreated1;
	private UserApp userCreated2;

	@BeforeEach
	void setUpPerTest() {
		userCreated2 = new UserApp();
		userCreated2.setFirstName("firstname2");
		userCreated2.setLastName("lastname2");
		userCreated2.setEmail("usertest5@email.com");
		userCreated2.setPassword("buddy");
		userCreated2.setAccount(new ArrayList<Account>());
		userCreated2.setContacts(new ArrayList<UserApp>());
		userCreated2.setTransactions(new ArrayList<Transaction>());
		userCreated2.setRole("USER");
		userAccountServiceUnderTest.createUser(userCreated2);

		userCreated1 = new UserApp();
		userCreated1.setFirstName("firstname");
		userCreated1.setLastName("lastname");
		userCreated1.setEmail("usertest@email.com");
		userCreated1.setPassword("buddy");
		userCreated1.setAccount(new ArrayList<Account>());
		userCreated1.setContacts(new ArrayList<UserApp>());
		userCreated1.setTransactions(new ArrayList<Transaction>());
		userCreated1.setRole("USER");

	}

	@Test
	void testCreateUser() throws Exception {
		try {
			userAccountServiceUnderTest.createUser(userCreated1);

			UserApp userFound = userAccountServiceUnderTest.getUserEntityByEmail("usertest@email.com");
			assertNotNull(userFound);
			assertEquals(userCreated1.getEmail(), userFound.getEmail());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testCreateUser_withDuplicatedEmailOfUserCreated_returnIllegalArgumentException() throws Exception {
		UserApp userCreated = new UserApp();
		userCreated.setId(3);
		userCreated.setFirstName("firstname");
		userCreated.setLastName("lastname");
		userCreated.setEmail("usertest@email.com");
		userCreated.setPassword("buddy");
		userCreated.setAccount(new ArrayList<Account>());
		userCreated.setContacts(new ArrayList<UserApp>());
		userCreated.setTransactions(new ArrayList<Transaction>());
		userCreated.setRole("USER");
		try {
			userAccountServiceUnderTest.createUser(userCreated);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> userAccountServiceUnderTest.createUser(userCreated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Transactional
	@Test
	void testAddUserContact() throws Exception {
		try {
			userAccountServiceUnderTest.createUser(userCreated1);
			UserApp resultlistContactCreated = userAccountServiceUnderTest.addUserContact("usertest5@email.com",
					"usertest@email.com");

			List<UserApp> listOfContactsOfUserFound = userAccountServiceUnderTest
					.getAllUserContacts("usertest@email.com");
			assertTrue(listOfContactsOfUserFound.contains(resultlistContactCreated));

		} catch (IllegalArgumentException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Transactional
	@Test
	void testAddUserContact_withDuplicatedContact_returnIllegalArgumentException() throws Exception {
		try {
			testAddUserContact();
			userAccountServiceUnderTest.addUserContact("usertest5@email.com", "usertest@email.com");
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> userAccountServiceUnderTest.addUserContact("usertest5@email.com", "usertest@email.com"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Transactional
	@Test
	void testAddUserContact_withContactNotFound_returnNullException() throws Exception {
		try {
			UserApp resultlistContactCreated = userAccountServiceUnderTest.addUserContact("usertest6@email.com",
					"usertest@email.com");

			assertNull(resultlistContactCreated);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> userAccountServiceUnderTest.addUserContact("usertest6@email.com", "usertest@email.com"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddBuddyAccount() throws Exception {
		try {
			BuddyAccount resultBuddyAccountCreated = userAccountServiceUnderTest.addBuddyAccount("usertest@email.com");

			assertAll("assertion of info Buddy Account created",
					() -> assertEquals(80.00, resultBuddyAccountCreated.getBalance()),
					() -> assertEquals("usertest@email.com", resultBuddyAccountCreated.getUser().getEmail()),
					() -> assertNotNull(resultBuddyAccountCreated.getId()));
		} catch (IllegalArgumentException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddBuddyAccount_withDuplicatedBuddyAccount_returnIllegalArgumentException() throws Exception {
		try {
			BuddyAccount resultBuddyAccountCreated = userAccountServiceUnderTest.addBuddyAccount("usertest@email.com");

			assertNull(resultBuddyAccountCreated.getId());
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> userAccountServiceUnderTest.addBuddyAccount("usertest@email.com"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBalanceBuddyAccount() throws Exception {
		try {
			userAccountServiceUnderTest.updateBalanceBuddyAccount(44, 85.00);

			BuddyAccount resultBuddyAccountUpdated = userAccountServiceUnderTest
					.findBuddyAccountByUser("usertest@email.com");
			assertAll("assertion of balance updated of Buddy Account ",
					() -> assertEquals(76016, resultBuddyAccountUpdated.getId()),
					() -> assertEquals(85.00, resultBuddyAccountUpdated.getBalance()),
					() -> assertEquals("usertest@email.com", resultBuddyAccountUpdated.getUser().getEmail()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBalanceBuddyAccount_withBuddyAccountNotFound_returnNullException() throws Exception {
		try {
			userAccountServiceUnderTest.updateBalanceBuddyAccount(42, 15.00);

			BuddyAccount noExistingBuddyAccount = userAccountServiceUnderTest
					.findBuddyAccountByUser("usertest5@email.com");
			assertNull(noExistingBuddyAccount.getCreation());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> userAccountServiceUnderTest.updateBalanceBuddyAccount(42, 15.00));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBalanceBankingAccount() throws Exception {
		try {
			userAccountServiceUnderTest.updateBalanceBankingAccount(2, 30.00);

			BankingAccount resultBankingAccountUpdated = userAccountServiceUnderTest
					.findBankingAccountByUser("testuser2@gmail.com");
			assertAll("assertion of balance updated of Buddy Account ",
					() -> assertEquals(76001, resultBankingAccountUpdated.getId()),
					() -> assertEquals(30.00, resultBankingAccountUpdated.getBalance()),
					() -> assertEquals("testuser2@gmail.com", resultBankingAccountUpdated.getUser().getEmail()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBalanceBankingAccount_withBankingAccountNotFound_returnNullException() throws Exception {
		try {
			userAccountServiceUnderTest.updateBalanceBankingAccount(42, 15.00);

			BankingAccount noExistingbankingAccount = userAccountServiceUnderTest
					.findBankingAccountByUser("usertest5@email.com");
			assertNull(noExistingbankingAccount.getCreation());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> userAccountServiceUnderTest.updateBalanceBankingAccount(42, 15.00));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

@Test
void testFindBuddyAccountByUser() throws Exception {
	try {
		BuddyAccount resultBuddyAccountFound = userAccountServiceUnderTest.findBuddyAccountByUser("usertest@email.com");
		
		assertAll("assertion info of  Buddy Account found ",
				() -> assertEquals(76016,resultBuddyAccountFound.getId()),	
				() -> assertNotNull(resultBuddyAccountFound.getCreation()),
				() -> assertEquals(85.00, resultBuddyAccountFound.getBalance()),
				() -> assertEquals("usertest@email.com", resultBuddyAccountFound.getUser().getEmail()));
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}

@Test
void testFindBuddyAccountByUser_withBuddyAccountNotFound_returnNullException()throws Exception {
	try {
		BuddyAccount buddyAccountNotFound = userAccountServiceUnderTest.findBuddyAccountByUser("usertest5@email.com");
		
		assertNull(buddyAccountNotFound.getCreation());
	} catch (NullPointerException e) {
				assertThrows(NullPointerException.class,
						() -> userAccountServiceUnderTest.findBuddyAccountByUser("usertest5@email.com"));
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}

@Test
void testFindBankingAccountByUser() throws Exception {
	try {
		BankingAccount resultBankingAccountFound = userAccountServiceUnderTest.findBankingAccountByUser("testuser2@gmail.com");
		
		assertAll("assertion info of  Banking Account found ",
				() -> assertEquals(76001,resultBankingAccountFound.getId()),	
				() -> assertNotNull(resultBankingAccountFound.getCreation()),
				() -> assertEquals(30.00, resultBankingAccountFound.getBalance()),
				() -> assertEquals("testuser2@gmail.com", resultBankingAccountFound.getUser().getEmail()));
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}


void testFindBankingAccountByUser_withBankingAccountNotFound_returnNullException()throws Exception {
	try {
		BankingAccount bankingAccountNotFound = userAccountServiceUnderTest.findBankingAccountByUser("usertest5@email.com");
		
		assertNull(bankingAccountNotFound .getCreation());
	} catch (NullPointerException e) {
				assertThrows(NullPointerException.class,
						() ->userAccountServiceUnderTest.findBankingAccountByUser("usertest5@email.com"));
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}
}
