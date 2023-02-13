package ru.yandex.practicum.filmorate.utilites;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest extends UserValidator {
    UserValidator validator = new UserValidator();

    @Test
    void validateUserShouldReturnTrueWhenUserIsCorrect() {
        User user = new User(0, "user1@email.ru", "User1", "FirstUser",
                LocalDate.of(2000, 12, 12));

        assertTrue(validator.validateUserEmail(user));
        assertTrue(validator.validateUserLogin(user));
        assertTrue(validator.validateUserBirthday(user));
    }

    @Test
    void validateUserShouldReturnFalseWhenUserIncorrect() {
        User user = new User(0, "usermail.ru", "us er1", "FirstUser",
                LocalDate.of(2100, 12, 21));

        assertFalse(validator.validateUserEmail(user));
        assertFalse(validator.validateUserLogin(user));
        assertFalse(validator.validateUserBirthday(user));

        User user1 = new User(0, "", "", "FirstUser",
                LocalDate.of(2015, 12, 20));

        assertFalse(validator.validateUserEmail(user1));
        assertFalse(validator.validateUserLogin(user1));
    }
}
