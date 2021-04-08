package it.codeful.exchange.userservice.util;

import it.codeful.exchange.userservice.data.AccountModel;
import it.codeful.exchange.userservice.data.Currency;

public final class Accounts {
    public static final String VALID_PESEL = "72022842113";

    public static AccountModel plnAccount() {
        return createAccount(VALID_PESEL, Currency.PLN);
    }

    private static AccountModel createAccount(String pesel, Currency currency) {
        return AccountModel.builder()
                .ownerPesel(pesel)
                .currency(currency)
                .build();
    }
}
