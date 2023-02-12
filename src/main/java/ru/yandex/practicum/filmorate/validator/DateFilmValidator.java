package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDate;

public class DateFilmValidator implements ConstraintValidator<DateFilm, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            return !value.isBefore(LocalDate.of(1985, 12, 28));
        }
    }
}