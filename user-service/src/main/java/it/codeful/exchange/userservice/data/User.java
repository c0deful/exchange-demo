package it.codeful.exchange.userservice.data;

import lombok.*;

@Data
@Builder
public class User {
    @NonNull String pesel;
    @NonNull String name;
}
