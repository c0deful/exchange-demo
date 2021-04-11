package it.codeful.exchange.userservice.exception;

public class UnsupportedCurrencyException extends RuntimeException {
    public UnsupportedCurrencyException(String currencyCode) {
        super(String.format("Currency=%s is unsupported", currencyCode));
    }
}
