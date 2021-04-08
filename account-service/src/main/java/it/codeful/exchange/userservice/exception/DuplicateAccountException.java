package it.codeful.exchange.userservice.exception;

import it.codeful.exchange.userservice.data.Currency;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String pesel, Currency currency) {
        super(String.format("Account for currency=%s and PESEL=%s already exists", currency, pesel));
    }
}
