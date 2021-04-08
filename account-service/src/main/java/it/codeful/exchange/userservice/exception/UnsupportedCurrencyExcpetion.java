package it.codeful.exchange.userservice.exception;

public class UnsupportedCurrencyExcpetion extends RuntimeException {
    public UnsupportedCurrencyExcpetion(String currencyCode) {
        super(String.format("Currency=%s is unsupported", currencyCode));
    }
}
