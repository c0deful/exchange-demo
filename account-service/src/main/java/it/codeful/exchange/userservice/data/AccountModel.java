package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.api.AccountView;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
public class AccountModel {
    private @NonNull @PESEL String ownerPesel;
    private @Min(0) BigDecimal amount;
    private @NonNull Currency currency;

    public AccountView toView() {
        return AccountView.builder()
                .pesel(ownerPesel)
                .amount(amount)
                .currencyCode(currency.toString())
                .build();
    }
}
