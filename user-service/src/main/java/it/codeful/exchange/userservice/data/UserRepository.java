package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public void create(@Valid User user) {
        User existingUser = users.putIfAbsent(user.getPesel(), user);
        if (existingUser != null) {
            throw new DuplicateUserException(user.getPesel());
        }
    }

    public User get(String pesel) {
        User result = users.get(pesel);
        if (result == null) {
            throw new UserNotFoundException(pesel);
        }
        return result;
    }
}
