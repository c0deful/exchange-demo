package it.codeful.exchange.userservice.api;

import it.codeful.exchange.userservice.data.AccountRepository;
import it.codeful.exchange.userservice.data.AccountView;
import it.codeful.exchange.userservice.data.Currency;
import it.codeful.exchange.userservice.exception.AccountNotFoundException;
import it.codeful.exchange.userservice.exception.DuplicateAccountException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@Slf4j
public class AccountApi {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountView account) {
        accountRepository.create(account.toModel());
    }

    @GetMapping("/account/{pesel}/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public AccountView getAccount(@PathVariable("pesel") String pesel, @PathVariable("currency") String currency) {
        return accountRepository.get(pesel, Currency.fromCode(currency)).toView();
    }

    @PostMapping("/account/{pesel}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBalance(@PathVariable("pesel") String pesel, @RequestBody ChangeBalanceCommand command) {
        boolean anyfallBelowZero = command.getBalanceChanges().entrySet().stream()
                .anyMatch(e -> fallsBelowZero(pesel, e.getKey(), e.getValue()));
        if (anyfallBelowZero) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Account balance must not fall below zero");
        }
        command.getBalanceChanges().forEach((curr, change) ->  {
            var account = accountRepository.get(pesel, curr);
            account.setAmount(account.getAmount().add(change).setScale(2, RoundingMode.DOWN));
        });
    }

    private boolean fallsBelowZero(String pesel, Currency currency, BigDecimal changeAmount) {
        return accountRepository.get(pesel, currency).getAmount().add(changeAmount).compareTo(BigDecimal.ZERO) < 0;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateAccount(DuplicateAccountException e) {
        log.info(e.getMessage());
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleAccountNotFound(AccountNotFoundException e) {
        log.info(e.getMessage());
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleInvalidData(ConstraintViolationException e) {
        return new ApiError(e.getMessage());
    }

    @Data
    @AllArgsConstructor
    public static final class ApiError {
        private String message;
    }
}
