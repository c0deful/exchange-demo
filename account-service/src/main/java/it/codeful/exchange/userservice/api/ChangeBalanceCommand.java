package it.codeful.exchange.userservice.api;

import it.codeful.exchange.userservice.data.Currency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
class ChangeBalanceCommand {
    private @NotEmpty Map<Currency, BigDecimal> balanceChanges;
}
