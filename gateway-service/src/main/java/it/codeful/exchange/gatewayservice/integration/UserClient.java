package it.codeful.exchange.gatewayservice.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(RestTemplateBuilder restTemplateBuilder, @Value("${host.user}") String apiHost) {
        restTemplate = restTemplateBuilder.rootUri(apiHost).build();
    }

    public void registerUser(String pesel, String firstName, String lastName) {
        restTemplate.postForLocation("/user", Map.of(
                "pesel", pesel,
                "firstName", firstName,
                "lastName", lastName
        ));
    }
}
