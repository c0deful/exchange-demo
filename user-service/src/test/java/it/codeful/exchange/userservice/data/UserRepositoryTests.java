package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import it.codeful.exchange.userservice.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.codeful.exchange.userservice.util.Users.VALID_PESEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryTests {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void createAndRetrieve() {
        UserModel createdUser = Users.user();
        userRepository.create(createdUser);
        UserModel retrievedUser = userRepository.get(createdUser.getPesel());
        assertEquals(createdUser, retrievedUser);
    }

    @Test
    void createDuplicateUser() {
        UserModel createdUser = Users.user();
        UserModel newUser = Users.user();
        userRepository.create(createdUser);
        assertThrows(DuplicateUserException.class, () -> userRepository.create(newUser));

    }

    @Test
    void retrieveMissingUser() {
        assertThrows(UserNotFoundException.class, () -> userRepository.get(VALID_PESEL));
    }
}
