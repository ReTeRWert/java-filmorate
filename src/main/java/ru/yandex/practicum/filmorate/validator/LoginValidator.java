package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class LoginValidator implements ConstraintValidator<Login, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !Objects.equals(null, value)
                && !value.isBlank()
                && !value.contains("\\\\u0020")
                && !value.contains("\t")
                && !value.contains(" ");
    }
}