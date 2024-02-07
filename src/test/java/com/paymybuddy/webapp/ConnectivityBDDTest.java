package com.paymybuddy.webapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
class ConnectivityBDDTest {
	@Test
	void testConnectionBDD() {
		Connection con= null;
		try {
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/paymybuddy_test?serverTimezone=Europe/Paris","buddyuser", "buddy");
			 assertNotNull(con);
		} catch (SQLException e) {
			e.getStackTrace();
			fail(e.getMessage());
		} catch (AssertionError ex) {
			fail(ex.getMessage());
		}
	}
}
