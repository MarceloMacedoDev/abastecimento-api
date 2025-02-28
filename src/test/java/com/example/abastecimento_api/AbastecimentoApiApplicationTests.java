package com.example.abastecimento_api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class AbastecimentoApiApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		assertNotNull(context);
	}

	@Test
	public void testApplicationConfig() {
		AbastecimentoApiApplication application = new AbastecimentoApiApplication();
		assertNotNull(application);
	}
}
