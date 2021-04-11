package it.codeful.exchange.gatewayservice.api;

import it.codeful.exchange.gatewayservice.account.AccountClient;
import it.codeful.exchange.gatewayservice.account.AccountView;
import it.codeful.exchange.gatewayservice.api.CustomerBalanceView;
import it.codeful.exchange.gatewayservice.api.ExchangeCurrencyCommand;
import it.codeful.exchange.gatewayservice.api.RegisterCustomerCommand;
import it.codeful.exchange.gatewayservice.exchangerate.ExchangeRateClient;
import it.codeful.exchange.gatewayservice.user.UserClient;
import it.codeful.exchange.gatewayservice.user.UserView;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@Validated
public class GatewayApi {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    @Autowired
    private UserClient userClient;

    @GetMapping("/customer/{pesel}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerBalanceView getCustomer(@PathVariable("pesel") @Valid @PESEL String pesel) {
        UserView user = userClient.getUser(pesel);
        AccountView plnAccount = accountClient.getAccount(pesel, "PLN");
        AccountView usdAccount = accountClient.getAccount(pesel, "USD");
        return CustomerBalanceView.builder()
                .pesel(pesel)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .balance(Map.of(
                        "PLN", plnAccount.getAmount(),
                        "USD", usdAccount.getAmount()))
                .build();
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@RequestBody @Valid RegisterCustomerCommand command) {
        // this is not atomic and would require event journaling or something of that nature to be totally 100% safe
        // DO NOT do this in production code
        userClient.registerUser(command.getPesel(), command.getFirstName(), command.getLastName());
        accountClient.createAccount(command.getPesel(), "PLN", command.getPln());
        accountClient.createAccount(command.getPesel(), "USD", BigDecimal.ZERO);
    }

    @PostMapping("/customer/{pesel}")
    @ResponseStatus(HttpStatus.CREATED)
    public void exchangeCurrency(@RequestBody @Valid ExchangeCurrencyCommand command, @PathVariable("pesel") @Valid @PESEL String pesel) {
        BigDecimal toAmount;
        if ("PLN".equals(command.getFromCurrencyCode())) {
            toAmount = exchangeRateClient.calculateAskAmount(command.getToCurrencyCode(), command.getFromAmount());
        } else if ("PLN".equals(command.getToCurrencyCode())) {
            toAmount = exchangeRateClient.calculateBidAmount(command.getFromCurrencyCode(), command.getFromAmount());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Only exchanges including PLN are supported");
        }
        accountClient.updateBalances(pesel, Map.of(
                command.getFromCurrencyCode(), command.getFromAmount().negate(),
                command.getToCurrencyCode(), toAmount
        ));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIntegrationError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
}
