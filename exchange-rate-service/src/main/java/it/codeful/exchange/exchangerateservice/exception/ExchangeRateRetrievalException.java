package it.codeful.exchange.exchangerateservice.exception;

public class ExchangeRateRetrievalException extends RuntimeException {
    public ExchangeRateRetrievalException(String isoCurrencyCode, Throwable cause) {
        super(String.format("Failed to retrieve exchange rates for currency=%s", isoCurrencyCode, cause));
    }
}
