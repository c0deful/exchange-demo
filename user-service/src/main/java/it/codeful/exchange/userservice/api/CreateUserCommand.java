package it.codeful.exchange.userservice.api;

import it.codeful.exchange.userservice.validation.MinAgePESEL;
import lombok.Data;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserCommand {
    private @PESEL @MinAgePESEL String pesel;
    private @NotBlank String firstName;
    private @NotBlank String lastName;
}
