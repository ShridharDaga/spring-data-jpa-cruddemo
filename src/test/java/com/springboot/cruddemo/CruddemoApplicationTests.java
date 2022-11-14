package com.springboot.cruddemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class CruddemoApplicationTests {

	@Test
	void contextLoads() {
		CruddemoApplication.main(new String[] {});
		assertTrue(true);
	}
}
