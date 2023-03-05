package ru.yandex.practicum.filmorate.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.model.Constants.CINEMA_BIRTHDAY;

public class DateFilmValidator implements ConstraintValidator<DateFilm, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return (value != null) && !value.isBefore(CINEMA_BIRTHDAY);
    }
}