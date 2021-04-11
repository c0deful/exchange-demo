package it.codeful.exchange.userservice.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {
    private String pesel;
    private String firstName;
    private String lastName;
}
