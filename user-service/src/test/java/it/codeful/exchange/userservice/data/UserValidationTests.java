package it.codeful.exchange.userservice.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserValidationTests {
    private static final String INVALID_PESEL = "72022842111";
    private static final String TOO_YOUNG_PESEL = "20250523881";

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void invalidPeselFormat() {
        User user = User.builder()
                .name("Jan Kowalski")
                .pesel(INVALID_PESEL)
                .build();
        Assertions.assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void invalidPeselAge() {
        User user = User.builder()
                .name("Jan Kowalski")
                .pesel(TOO_YOUNG_PESEL)
                .build();
        Assertions.assertEquals(1, validator.validate(user).size());
    }
}
