package it.codeful.exchange.userservice.util;

import com.github.javafaker.Faker;
import it.codeful.exchange.userservice.data.UserModel;

public final class Users {
    public static final String VALID_PESEL = "72022842113";
    private static final String INVALID_PESEL = "72022842111";
    private static final String TOO_YOUNG_PESEL = "20250523881";

    private static final Faker faker = Faker.instance();

    public static UserModel user() {
        return createUser(VALID_PESEL);
    }

    public static UserModel invalidPesel() {
        return createUser(INVALID_PESEL);
    }

    public static UserModel tooYoung() {
        return createUser(TOO_YOUNG_PESEL);
    }

    private static UserModel createUser(String pesel) {
        return UserModel.builder()
                .pesel(pesel)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }
}
