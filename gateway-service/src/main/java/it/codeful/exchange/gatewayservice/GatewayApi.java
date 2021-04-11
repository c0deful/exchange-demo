package it.codeful.exchange.gatewayservice;

import it.codeful.exchange.gatewayservice.integration.AccountClient;
import it.codeful.exchange.gatewayservice.integration.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public void registerCustomer(@Valid RegisterCustomerCommand command) {
        // this is not atomic and would require event journaling or something of that nature to be totally 100% safe
        // DO NOT do this in production code
        userClient.registerUser(command.getPesel(), command.getFirstName(), command.getLastName());
        accountClient.createAccount(command.getPesel(), "PLN", command.getPlnStartingAmount());
        accountClient.createAccount(command.getPesel(), "USD", BigDecimal.ZERO);
    }
}
