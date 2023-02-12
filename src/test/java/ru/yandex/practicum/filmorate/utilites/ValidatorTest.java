package ru.yandex.practicum.filmorate.utilites;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest extends Validator {

    Validator validator = new Validator();

    private Film createCorrectFilm() {
        return new Film(1, "film1", "desc",
                LocalDate.of(2002, 5, 23), 120);
    }

    private Film createIncorrectFilm() {
        return new Film(0, "", "", LocalDate.of(1855, 2, 12),
                -103);
    }

    private User createCorrectUser() {
        return new User(0, "user1@email.ru", "User1", "FirstUser",
                LocalDate.of(2000, 12, 12));
    }

    private User createUserWithIncorrectEmailLoginBirthday() {
        return new User(0, "usermail.ru", "us er1", "FirstUser",
                LocalDate.of(2100, 12, 21));
    }

    private User createUserWithEmptyEmailLogin() {
        return new User(0, "", "", "FirstUser",
                LocalDate.of(2015, 12, 20));
    }

    @Test
    void validateFilmShouldReturnTrueWhenFilmCorrect() {
        Film film = createCorrectFilm();
        assertTrue(validator.validateFilmName(film));
        assertTrue(validator.validateFilmDescription(film));
        assertTrue(validator.validateFilmReleaseDate(film));
        assertTrue(validator.validateFilmDuration(film));
    }

    @Test
    void validateFilmShouldReturnFalseWhenFieldsIncorrect() {
        Film film = createIncorrectFilm();

        assertFalse(validator.validateFilmName(film));
        assertFalse(validator.validateFilmReleaseDate(film));
        assertFalse(validator.validateFilmDuration(film));

    }


    @Test
    void validateUserShouldReturnTrueWhenUserIsCorrect() {
        User user = createCorrectUser();

        assertTrue(validator.validateUserEmail(user));
        assertTrue(validator.validateUserLogin(user));
        assertTrue(validator.validateUserBirthday(user));
    }

    @Test
    void validateUserShouldReturnFalseWhenUserIncorrect() {
        User user = createUserWithIncorrectEmailLoginBirthday();

        assertFalse(validator.validateUserEmail(user));
        assertFalse(validator.validateUserLogin(user));
        assertFalse(validator.validateUserBirthday(user));

        User user1 = createUserWithEmptyEmailLogin();

        assertFalse(validator.validateUserEmail(user1));
        assertFalse(validator.validateUserLogin(user1));
    }
}