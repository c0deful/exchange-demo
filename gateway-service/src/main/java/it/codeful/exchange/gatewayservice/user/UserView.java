package it.codeful.exchange.gatewayservice.user;

import lombok.Data;

@Data
public class UserView {
    private String pesel;
    private String firstName;
    private String lastName;
}
