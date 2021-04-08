package it.codeful.exchange.userservice;

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

import javax.validation.ConstraintViolationException;

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

    @GetMapping("/account/{currency}/{pesel}")
    @ResponseStatus(HttpStatus.OK)
    public AccountView getAccount(@PathVariable("currency") String currency, @PathVariable("pesel") String pesel) {
        return accountRepository.get(pesel, Currency.fromCode(currency)).toView();
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
