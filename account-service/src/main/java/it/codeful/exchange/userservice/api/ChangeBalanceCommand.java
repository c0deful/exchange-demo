package it.codeful.exchange.userservice.api;

import it.codeful.exchange.userservice.data.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
class ChangeBalanceCommand {
    private Map<Currency, BigDecimal> balanceChanges;
}
