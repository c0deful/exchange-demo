package it.codeful.exchange.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.codeful.exchange.userservice.data.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountServiceApplication.class)
@AutoConfigureMockMvc
class AccountServiceApiTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private AccountRepository repository;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		repository.clear();
	}
}
