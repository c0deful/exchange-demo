package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryTests {

    private static final String VALID_PESEL = "72022842113";

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void createAndRetrieve() {
        User createdUser = User.builder()
                .name("Jan Kowalski")
                .pesel(VALID_PESEL)
                .build();
        userRepository.create(createdUser);
        User retrievedUser = userRepository.get(VALID_PESEL);
        assertEquals(createdUser, retrievedUser);
    }

    @Test
    public void createInvalidAge() {
        // TODO
    }

    @Test
    public void createDuplicateUser() {
        User createdUser = User.builder()
                .name("Jan Kowalski")
                .pesel(VALID_PESEL)
                .build();
        User newUser = User.builder()
                .name("Joanna Nowak")
                .pesel(VALID_PESEL)
                .build();
        userRepository.create(createdUser);
        assertThrows(DuplicateUserException.class, () -> userRepository.create(newUser));

    }

    @Test
    public void retrieveMissingUser() {
        assertThrows(UserNotFoundException.class, () -> userRepository.get(VALID_PESEL));
    }
}
