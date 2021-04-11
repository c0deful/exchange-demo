package it.codeful.exchange.userservice.data;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
public class AccountModel {
    @NonNull @PESEL String ownerPesel;
    @Min(0) BigDecimal amount;
    @NonNull Currency currency;

    public AccountView toView() {
        return AccountView.builder()
                .pesel(ownerPesel)
                .amount(amount)
                .currencyCode(currency.toString())
                .build();
    }
}
