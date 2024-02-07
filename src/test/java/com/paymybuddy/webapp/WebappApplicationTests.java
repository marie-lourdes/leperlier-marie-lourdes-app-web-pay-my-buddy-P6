package com.paymybuddy.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class WebappApplicationTests {

	@Test
	void contextLoads() {
		assertEquals("6.1.1",SpringVersion.getVersion());
	}

}
