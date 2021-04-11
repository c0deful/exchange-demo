package it.codeful.exchange.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.codeful.exchange.userservice.api.AccountView;
import it.codeful.exchange.userservice.api.CreateAccountCommand;
import it.codeful.exchange.userservice.data.AccountRepository;
import it.codeful.exchange.userservice.util.Accounts;
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

	@Test
	void createAndRetrieveAccount() throws Exception {
		CreateAccountCommand command = Accounts.createAccountCommand();
		mvc.perform(
				post("/account")
						.content(objectMapper.writeValueAsBytes(command))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mvc.perform(
				get("/account/{pesel}/PLN", command.getPesel()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.pesel").value(command.getPesel()))
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.currencyCode").value(command.getCurrencyCode()));
	}

	@Test
	void createDuplicateAccount() throws Exception {
		CreateAccountCommand command = Accounts.createAccountCommand();
		mvc.perform(
				post("/account")
						.content(objectMapper.writeValueAsBytes(command))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mvc.perform(
				post("/account")
						.content(objectMapper.writeValueAsBytes(command))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
}
