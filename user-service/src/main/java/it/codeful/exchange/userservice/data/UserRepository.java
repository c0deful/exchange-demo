package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public void create(User user) {
        users.put(user.getPesel(), user);
    }

    public User get(String pesel) {
        return Optional.ofNullable(users.get(pesel))
                        .orElseThrow(() -> new UserNotFoundException(pesel));
    }
}
