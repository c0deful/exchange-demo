package it.codeful.exchange.exchangerateservice.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.codeful.exchange.exchangerateservice.exception.ExchangeRateRetrievalException;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class NbpApiClient {

    private static final String HOST = "http://api.nbp.pl";

    private final RestTemplate restTemplate = new RestTemplate();
    private final LoadingCache<String, NbpExchangeRate> exchangeRates = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .maximumSize(100)
            .build(CacheLoader.from(this::loadExchangeRateForCurrency));

    public BigDecimal getAskExchangeRate(String isoCurrencyCode) {
        return getExchangeRateForCurrency(isoCurrencyCode).getAsk();
    }

    public BigDecimal getBidExchangeRate(String isoCurrencyCode) {
        return getExchangeRateForCurrency(isoCurrencyCode).getBid();
    }

    private NbpExchangeRate getExchangeRateForCurrency(String isoCurrencyCode) {
        try {
            return exchangeRates.get(isoCurrencyCode);
        } catch (ExecutionException e) {
            throw new ExchangeRateRetrievalException(isoCurrencyCode, e);
        }
    }

    private NbpExchangeRate loadExchangeRateForCurrency(String isoCurrencyCode) {
        return Objects.requireNonNull(restTemplate.getForObject(HOST + "/api/exchangerates/rates/c/{isoCurrencyCode}",
                NbpApiResponse.class, isoCurrencyCode))
                .getRates()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Data
    private static class NbpApiResponse {
        String code;
        List<NbpExchangeRate> rates;
    }

    @Data
    private static class NbpExchangeRate {
        BigDecimal ask;
        BigDecimal bid;
    }
}
