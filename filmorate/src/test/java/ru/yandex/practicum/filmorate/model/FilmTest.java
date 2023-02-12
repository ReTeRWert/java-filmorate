package ru.yandex.practicum.filmorate.model;

import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import ru.yandex.practicum.filmorate.validator.DateFilmValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilmTest {
    
    private Film film;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void beforeEach() {
        film = new Film.FilmBuilder()
                .id(null)
                .name("name")
                .description("")
                .releaseDate(LocalDate.of(1999,9,9))
                .duration(99L)
                .build();
    }

    @DisplayName("MaxSize description validation")
    @Test
    void checkDescriptionValidation() {
        film.setDescription(Stream.generate(() ->("*")).limit(201).collect(Collectors.joining()));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertEquals("Максимальная длина описания — 200 символов", violations.iterator().next().getMessage());
    }

    @DisplayName("NotBlank name validation")
    @Test
    void checkNameIsNotBlank(){
        film.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertEquals("Название не может быть пустым;",violations.iterator().next().getMessage());
    }

    @DisplayName("Pozitive duration validation")
    @Test
    void checkPositiveDurationValidation(){
        film.setDuration(-1L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertEquals("Продолжительность фильма должна быть положительной.",violations.iterator().next().getMessage());
    }
}
