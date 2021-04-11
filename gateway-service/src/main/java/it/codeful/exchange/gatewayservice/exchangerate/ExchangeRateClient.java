package it.codeful.exchange.gatewayservice.exchangerate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ExchangeRateClient {
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateClient(RestTemplateBuilder restTemplateBuilder, @Value("${host.exchange-rate}") String apiHost) {
        restTemplate = restTemplateBuilder.rootUri(apiHost).build();
    }

    public BigDecimal calculateAskAmount(String isoCurrencyCode, BigDecimal amount) {
        return restTemplate.getForObject("/{currency}/ask?amount={amount}", BigDecimal.class, isoCurrencyCode, amount);
    }

    public BigDecimal calculateBidAmount(String isoCurrencyCode, BigDecimal amount) {
        return restTemplate.getForObject("/{currency}/bid?amount={amount}", BigDecimal.class, isoCurrencyCode, amount);
    }
}
