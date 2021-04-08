package it.codeful.exchange.userservice.exception;

import it.codeful.exchange.userservice.data.Currency;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String pesel, Currency currency) {
        super(String.format("No account for currency=%s and PESEL=%s exists", currency, pesel));
    }
}
