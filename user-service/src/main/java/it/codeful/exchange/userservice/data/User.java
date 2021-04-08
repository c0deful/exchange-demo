package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.validation.MinAgePESEL;
import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

@Data
@Builder
public class User {
    @NonNull @PESEL @MinAgePESEL String pesel;
    @NonNull String name;
}
