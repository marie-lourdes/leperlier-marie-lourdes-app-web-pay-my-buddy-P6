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
import com.paymybuddy.webapp.domain.model.BuddyAccount;
import com.paymybuddy.webapp.domain.model.Transaction;
import com.paymybuddy.webapp.domain.model.UserApp;

@SpringBootTest
class UserAccountServiceTest {
	@Autowired
	private UserAccountService userAccountServiceUnderTest;

	private UserApp userCreated;
	private UserApp contactCreated;

	@BeforeEach
	void setUpPerTest() {
		contactCreated = new UserApp();
		contactCreated.setFirstName("firstname2");
		contactCreated.setLastName("lastname2");
		contactCreated.setEmail("usertest5@email.com");
		contactCreated.setPassword("buddy");
		contactCreated.setAccount(new ArrayList<Account>());
		contactCreated.setContacts(new ArrayList<UserApp>());
		contactCreated.setTransactions(new ArrayList<Transaction>());
		contactCreated.setRole("USER");
		userAccountServiceUnderTest.createUser(contactCreated);

		userCreated = new UserApp();
		userCreated.setFirstName("firstname");
		userCreated.setLastName("lastname");
		userCreated.setEmail("usertest@email.com");
		userCreated.setPassword("buddy");
		userCreated.setAccount(new ArrayList<Account>());
		// contactsUser.add(contactCreated);
		userCreated.setContacts(new ArrayList<UserApp>());
		userCreated.setTransactions(new ArrayList<Transaction>());
		userCreated.setRole("USER");

	}

	@Test
	void testCreateUser() throws Exception {
		try {
			userAccountServiceUnderTest.createUser(userCreated);
			UserApp userFound = userAccountServiceUnderTest.getUserEntityByEmail("usertest@email.com");
			assertNotNull(userFound);
			assertEquals(userCreated.getEmail(), userFound.getEmail());
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
			userAccountServiceUnderTest.createUser(userCreated);
			UserApp resultlistContactCreated = userAccountServiceUnderTest.addUserContact("usertest5@email.com",
					"usertest@email.com");
			System.out.println("resultlistContactCreated" + resultlistContactCreated);
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
	void testAddBuddyAccount_withDuplicatedContact_returnIllegalArgumentException() throws Exception {
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
			BuddyAccount resultBuddyAccountCreated = userAccountServiceUnderTest.updateBalanceBuddyAccount(0, 0);
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

}
