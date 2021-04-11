package it.codeful.exchange.gatewayservice.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountView {
    private String pesel;
    private String currencyCode;
    private BigDecimal amount;
}
