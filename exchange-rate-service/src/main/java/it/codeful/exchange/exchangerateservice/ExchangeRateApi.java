package it.codeful.exchange.exchangerateservice;

import it.codeful.exchange.exchangerateservice.exception.ExchangeRateRetrievalException;
import it.codeful.exchange.exchangerateservice.integration.NbpApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@Slf4j
public class ExchangeRateApi {

    @Autowired
    private NbpApiClient client;

    @GetMapping("/{isoCurrencyCode}/bid")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateBidAmount(@PathVariable("isoCurrencyCode") String isoCurrencyCode, @RequestParam BigDecimal amount) {
        return client.getBidExchangeRate(isoCurrencyCode).multiply(amount).setScale(2, RoundingMode.DOWN);
    }

    @GetMapping("/{isoCurrencyCode}/ask")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateAskAmount(@PathVariable("isoCurrencyCode") String isoCurrencyCode, @RequestParam BigDecimal amount) {
        return client.getAskExchangeRate(isoCurrencyCode).multiply(amount).setScale(2, RoundingMode.DOWN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleExchangeRateRetrievalFailure(ExchangeRateRetrievalException e) {
        log.error(e.getMessage(), e);
    }
}
