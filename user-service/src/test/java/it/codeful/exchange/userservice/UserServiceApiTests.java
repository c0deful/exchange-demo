package it.codeful.exchange.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.codeful.exchange.userservice.data.UserRepository;
import it.codeful.exchange.userservice.api.UserView;
import it.codeful.exchange.userservice.util.FixedClockConfig;
import it.codeful.exchange.userservice.util.Users;
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

@SpringBootTest(classes = { UserServiceApplication.class, FixedClockConfig.class })
@AutoConfigureMockMvc
class UserServiceApiTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserRepository repository;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		repository.clear();
	}

	@Test
	void createAndRetrieveUser() throws Exception {
		UserView createdUser = Users.user().toView();
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(createdUser))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mvc.perform(
				get("/user/{pesel}", createdUser.getPesel()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.pesel").value(createdUser.getPesel()))
				.andExpect(jsonPath("$.firstName").value(createdUser.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(createdUser.getLastName()));
	}

	@Test
	void createDuplicateUser() throws Exception {
		UserView createdUser = Users.user().toView();
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(createdUser))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(createdUser))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	void invalidPesel() throws Exception {
		UserView user = Users.invalidPesel().toView();
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		mvc.perform(
				get("/user/{pesel}", user.getPesel()))
				.andExpect(status().isBadRequest());
	}

	@Test
	void userTooYoung() throws Exception {
		UserView user = Users.tooYoung().toView();
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		mvc.perform(
				get("/user/{pesel}", user.getPesel()))
				.andExpect(status().isNotFound());
	}
}
