package it.codeful.exchange.gatewayservice.api;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
class RegisterCustomerCommand {
    private @NonNull @PESEL String pesel;
    private @NotBlank String firstName;
    private @NotBlank String lastName;
    private @NonNull @Min(0) BigDecimal pln;
}
