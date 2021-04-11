package it.codeful.exchange.userservice.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountView {
    private String pesel;
    private BigDecimal amount;
    private String currencyCode;
}
