package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Component
@Validated
public class UserRepository {

    private final Map<String, UserModel> users = new HashMap<>();

    public void create(@Valid UserModel user) {
        UserModel existingUser = users.putIfAbsent(user.getPesel(), user);
        if (existingUser != null) {
            throw new DuplicateUserException(user.getPesel());
        }
    }

    public UserModel get(@Valid @PESEL String pesel) {
        UserModel result = users.get(pesel);
        if (result == null) {
            throw new UserNotFoundException(pesel);
        }
        return result;
    }
}
