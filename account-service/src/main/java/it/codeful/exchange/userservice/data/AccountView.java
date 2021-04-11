package it.codeful.exchange.userservice.data;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountView {
    private String pesel;
    private BigDecimal amount;
    private String currencyCode;

    public AccountModel toModel() {
        return AccountModel.builder()
                .ownerPesel(pesel)
                .amount(amount)
                .currency(Currency.fromCode(currencyCode))
                .build();
    }
}
