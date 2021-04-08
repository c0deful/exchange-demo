package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.exception.DuplicateAccountException;
import it.codeful.exchange.userservice.exception.AccountNotFoundException;
import it.codeful.exchange.userservice.util.Accounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static it.codeful.exchange.userservice.util.Accounts.VALID_PESEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountRepositoryTests {
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    void createAndRetrieve() {
        AccountModel createdAccount = Accounts.plnAccount();
        createdAccount.setAmount(BigDecimal.valueOf(100));
        accountRepository.create(createdAccount);
        AccountModel retrievedAccount = accountRepository.get(createdAccount.getOwnerPesel(), createdAccount.getCurrency());
        assertEquals(createdAccount, retrievedAccount);
        assertEquals(BigDecimal.ZERO, retrievedAccount.getAmount());
    }

    @Test
    void createDuplicate() {
        AccountModel createdAccount = Accounts.plnAccount();
        createdAccount.setAmount(BigDecimal.valueOf(100));
        accountRepository.create(createdAccount);
        AccountModel newAccount = Accounts.plnAccount();
        assertThrows(DuplicateAccountException.class, () -> accountRepository.create(newAccount));

    }

    @Test
    void retrieveMissing() {
        assertThrows(AccountNotFoundException.class, () -> accountRepository.get(VALID_PESEL, Currency.USD));
    }
}
