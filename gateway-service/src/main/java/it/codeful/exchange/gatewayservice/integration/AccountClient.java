package it.codeful.exchange.gatewayservice.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountClient {

    private final RestTemplate restTemplate;

    @Autowired
    public AccountClient(RestTemplateBuilder restTemplateBuilder, @Value("${host.account}") String apiHost) {
        restTemplate = restTemplateBuilder.rootUri(apiHost).build();
    }

    public void createAccount(String pesel, String isoCurrencyCode, BigDecimal startingAmount) {
        restTemplate.postForLocation("/account", Map.of(
                "pesel", pesel,
                "isoCurrencyCode", isoCurrencyCode,
                "amount", startingAmount
        ));
    }
}
