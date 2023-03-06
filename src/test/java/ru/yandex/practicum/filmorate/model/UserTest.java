package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

public class UserTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User.UserBuilder()
                .id(null)
                .email("1@mail.com")
                .login("TestLogin")
                .name("")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
    }

    @DisplayName("e-m@il validation")
    @ParameterizedTest
    @CsvSource({
            "'', Can not be blank",
            "null, Can not be blank",
            "ma il, *@*.*",
            "mail@@, *@*.*",
            "mail@, *@*.*",
            "@mail, *@*.*"
    })
    void checkEmailValidation(String value, String e) {
        if ("null".equals(value)) {
            value = null;
        }
        user.setEmail(value);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(e, violations.iterator().next().getMessage());
    }

    @DisplayName("Login validation")
    @ParameterizedTest
    @CsvSource({
            "'', 1, логин не может быть пустым и содержать пробелы",
            "' login', 1, логин не может быть пустым и содержать пробелы",
            "'login ', 1, логин не может быть пустым и содержать пробелы",
            "'l\togin', 1, логин не может быть пустым и содержать пробелы",
            "l ogin, 1, логин не может быть пустым и содержать пробелы",
            "login, 0, OK",
    })
    void checkLoginValidation(String testLogin, int expectSize, String expected) {
        user.setLogin(testLogin);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(expectSize, violations.size());
        if (!violations.isEmpty()) {
            Assertions.assertEquals(expected, violations.iterator().next().getMessage());
        }
    }

    @DisplayName("Birthday validation")
    @Test
    void checkBirthdayValidation() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals("День рождения не может быть в будущем", violations.iterator().next().getMessage());
    }

    @DisplayName("Blank name:login test")
    @Test
    void checkBlankNameEqualsLogin() {
        Assertions.assertEquals(user.getLogin(), user.getName(), "имя для отображения может быть пустым — в таком случае будет использован логин");
    }
}
