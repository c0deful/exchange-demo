package it.codeful.exchange.userservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class MinAgePESELValidator implements ConstraintValidator<MinAgePESEL, CharSequence> {

    @Override
    public void initialize(MinAgePESEL constraintAnnotation) {
        // TODO refactor constraint to accept minimum age as argument
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        int century = 19 + Integer.parseInt(charSequence, 2, 3, 10) / 2;
        int year = Integer.parseInt(charSequence, 0, 2, 10);
        int month = Integer.parseInt(charSequence, 2, 4, 10) % 20;
        int day = Integer.parseInt(charSequence, 4, 6, 10);
        String dateText = String.format("%02d%02d%02d%02d", century, year, month, day);
        LocalDate birthDate = LocalDate.parse(dateText, DateTimeFormatter.BASIC_ISO_DATE);
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}

