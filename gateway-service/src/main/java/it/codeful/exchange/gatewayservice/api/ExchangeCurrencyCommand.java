package it.codeful.exchange.gatewayservice.api;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
class ExchangeCurrencyCommand {
    private @NonNull String fromCurrencyCode;
    private @NonNull String toCurrencyCode;
    private @NonNull @Min(0) BigDecimal fromAmount;
}
