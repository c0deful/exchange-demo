package it.codeful.exchange.userservice.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String pesel) {
        super(String.format("User with PESEL=%s already exists", pesel));
    }
}
