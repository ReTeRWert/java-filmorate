package ru.yandex.practicum.filmorate.utilites;

import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {

    public void validateUser(User user) throws ValidateException {
        if (!validateUserEmail(user)) {
            throw new ValidateException("Email не может быть пустым и должен содержать символ @.");
        }
        if (!validateUserLogin(user)) {
            throw new ValidateException("Логин не может быть пустым и не должен содержать пробелов.");
        }
        if (!validateUserBirthday(user)) {
            throw new ValidateException("Дата рождения не может находиться в будущем.");
        }
        validateUserName(user);
        System.out.println("Данные пользователя прошли валидацию.");
    }

    protected boolean validateUserEmail(User user) {
        if (user.getEmail() == null) {
            return false;
        } else if (user.getEmail().isBlank()) {
            return false;
        }
        return user.getEmail().contains("@");
    }

    protected boolean validateUserLogin(User user) {
        if (user.getLogin() == null) {
            return false;
        } else if (user.getLogin().isBlank()) {
            return false;
        }
        return !user.getLogin().contains(" ");
    }

    private void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            System.out.println("Имя пользователя не указано, оно будет совпадать с логином.");
            user.setName(user.getLogin());
        }
    }

    protected boolean validateUserBirthday(User user) {
        if (user.getBirthday() == null) {
            return false;
        }
        return !user.getBirthday().isAfter(LocalDate.now());
    }
}
