package it.codeful.exchange.gatewayservice;

import it.codeful.exchange.gatewayservice.integration.AccountClient;
import it.codeful.exchange.gatewayservice.integration.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@Validated
public class GatewayApi {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private UserClient userClient;

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@RequestBody @Valid RegisterCustomerCommand command) {
        // this is not atomic and would require event journaling or something of that nature to be totally 100% safe
        // DO NOT do this in production code
        userClient.registerUser(command.getPesel(), command.getFirstName(), command.getLastName());
        accountClient.createAccount(command.getPesel(), "PLN", command.getPln());
        accountClient.createAccount(command.getPesel(), "USD", BigDecimal.ZERO);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIntegrationError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
}
