package it.codeful.exchange.userservice.data;

import it.codeful.exchange.userservice.validation.MinAgePESEL;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserModel {
    @NonNull @PESEL @MinAgePESEL String pesel;
    @NonNull @NotBlank String firstName;
    @NonNull @NotBlank String lastName;

    public UserView toView() {
        return UserView.builder()
                .pesel(pesel)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
