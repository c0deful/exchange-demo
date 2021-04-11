package it.codeful.exchange.userservice.util;

import it.codeful.exchange.userservice.api.CreateAccountCommand;
import it.codeful.exchange.userservice.data.AccountModel;
import it.codeful.exchange.userservice.data.Currency;

import java.math.BigDecimal;

public final class Accounts {
    public static final String VALID_PESEL = "72022842113";

    public static CreateAccountCommand createAccountCommand() {
        return CreateAccountCommand.builder()
                .pesel(VALID_PESEL)
                .currencyCode("PLN")
                .amount(BigDecimal.valueOf(100))
                .build();
    }

    public static AccountModel plnAccount() {
        return AccountModel.builder()
                .ownerPesel(Accounts.VALID_PESEL)
                .currency(Currency.PLN)
                .build();
    }
}
