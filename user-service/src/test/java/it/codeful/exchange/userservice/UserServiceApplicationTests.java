package it.codeful.exchange.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.codeful.exchange.userservice.data.UserModel;
import it.codeful.exchange.userservice.util.FixedClockConfig;
import it.codeful.exchange.userservice.util.Users;
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
class UserServiceApplicationTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void createAndRetrieveUser() throws Exception {
		UserModel createdUser = Users.user();
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
	void createUserInvalidPesel() throws Exception {
		UserModel createdUser = Users.invalidPesel();
		mvc.perform(
				post("/user")
						.content(objectMapper.writeValueAsBytes(createdUser))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		mvc.perform(
				get("/user/{pesel}", createdUser.getPesel()))
				.andExpect(status().isNotFound());
	}
}
