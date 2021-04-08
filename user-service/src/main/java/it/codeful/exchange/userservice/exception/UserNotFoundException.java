package it.codeful.exchange.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String pesel) {
        super(String.format("No user with PESEL=%s exists", pesel));
    }
}
