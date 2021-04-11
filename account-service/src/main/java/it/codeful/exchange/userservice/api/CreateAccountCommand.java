package it.codeful.exchange.userservice.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateAccountCommand {
    private String pesel;
    private String currencyCode;
    private BigDecimal amount;
}
