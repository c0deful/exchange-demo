package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.UnsupportedCurrencyException;

public enum Currency {
    PLN, USD;

    public static Currency fromCode(String code) {
        try {
            return Currency.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCurrencyException(code);
        }
    }
}
