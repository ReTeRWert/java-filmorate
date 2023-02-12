package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DateFilmValidator.class)
@Documented
public @interface DateFilm {
    String value() default "1895-12-28";

    String message() default "Ок";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}