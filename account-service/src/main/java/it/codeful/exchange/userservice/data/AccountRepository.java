package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.AccountNotFoundException;
import it.codeful.exchange.userservice.exception.DuplicateAccountException;
import lombok.Value;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Validated
public class AccountRepository {

    private final Map<AccountKey, AccountModel> accounts = new HashMap<>();

    public void create(@Valid AccountModel account) {
        account.setAmount(BigDecimal.ZERO);
        AccountKey key = new AccountKey(account.getOwnerPesel(), account.getCurrency());
        AccountModel existingAccount = accounts.putIfAbsent(key, account);
        if (existingAccount != null) {
            throw new DuplicateAccountException(key.getOwnerPesel(), key.getCurrency());
        }
    }

    public AccountModel get(@Valid @PESEL String ownerPesel, Currency currency) {
        AccountKey key = new AccountKey(ownerPesel, currency);
        AccountModel result = accounts.get(key);
        if (result == null) {
            throw new AccountNotFoundException(key.getOwnerPesel(), key.getCurrency());
        }
        return result;
    }

    public void clear() {
        accounts.clear();
    }

    @Value
    private static class AccountKey {
        String ownerPesel;
        Currency currency;
    }
}
