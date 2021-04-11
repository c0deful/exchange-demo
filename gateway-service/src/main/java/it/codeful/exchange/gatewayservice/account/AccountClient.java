package it.codeful.exchange.gatewayservice.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountClient {

    private final RestTemplate restTemplate;

    @Autowired
    public AccountClient(RestTemplateBuilder restTemplateBuilder, @Value("${host.account}") String apiHost) {
        restTemplate = restTemplateBuilder.rootUri(apiHost)
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void createAccount(String pesel, String isoCurrencyCode, BigDecimal startingAmount) {
        restTemplate.postForLocation("/account", Map.of(
                "pesel", pesel,
                "currencyCode", isoCurrencyCode,
                "amount", startingAmount
        ));
    }

    public AccountView getAccount(String pesel, String isoCurrencyCode) {
        return restTemplate.getForObject("/account/{pesel}/{currencyCode}", AccountView.class, pesel, isoCurrencyCode);
    }

    public void updateBalances(String pesel, Map<String, BigDecimal> balanceUpdates) {
        restTemplate.postForLocation("/account/{pesel}", Map.of("balanceChanges", balanceUpdates), pesel);
    }
}
