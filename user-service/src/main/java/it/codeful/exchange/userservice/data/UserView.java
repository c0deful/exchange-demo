package it.codeful.exchange.userservice.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {
    private String pesel;
    private String firstName;
    private String lastName;

    public UserModel toModel() {
        return UserModel.builder()
                .pesel(pesel)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
