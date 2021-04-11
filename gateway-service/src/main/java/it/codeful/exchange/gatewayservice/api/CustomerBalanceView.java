package it.codeful.exchange.gatewayservice.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
class CustomerBalanceView {
    private String pesel;
    private String firstName;
    private String lastName;
    private Map<String, BigDecimal> balance;
}
