package ru.yandex.practicum.filmorate.utilites;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmValidatorTest extends FilmValidator {

    FilmValidator validator = new FilmValidator();
    HashSet<Integer> likes = new HashSet<>();

    @Test
    void validateFilmShouldReturnTrueWhenFilmCorrect() {
        Film film = new Film(1, "film1", "desc",
                LocalDate.of(2002, 5, 23), 120, likes);

        assertTrue(validator.validateFilmName(film));
        assertTrue(validator.validateFilmDescription(film));
        assertTrue(validator.validateFilmReleaseDate(film));
        assertTrue(validator.validateFilmDuration(film));
    }

    @Test
    void validateFilmShouldReturnFalseWhenFieldsIncorrect() {
        Film film = new Film(0, "", "", LocalDate.of(1855, 2, 12),
                -103, likes);

        assertFalse(validator.validateFilmName(film));
        assertFalse(validator.validateFilmReleaseDate(film));
        assertFalse(validator.validateFilmDuration(film));
    }



}