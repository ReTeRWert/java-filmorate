package ru.yandex.practicum.filmorate.utilites;

import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    private static final LocalDate movieBirthday = LocalDate.of(1895, 12, 28);
    private boolean isValid = true;

    public void validateFilm(Film film) throws ValidateException {
        validateFilmName(film);
        validateFilmDescription(film);
        validateFilmReleaseDate(film);
        validateFilmDuration(film);

        if (!isValid) {
            throw new ValidateException("Фильм не прошел валидацию.");
        }

        System.out.println("Фильм прошел валидацию.");
    }

    protected boolean validateFilmName(Film film) {

        if (film.getName().isBlank()) {
            isValid = false;
        }
        return isValid;
    }

    protected boolean validateFilmDescription(Film film) {
        if (film.getDescription().length() > 200) {
            isValid = false;
        }
        return isValid;
    }

    protected boolean validateFilmReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(movieBirthday)) {
            isValid = false;
        }
        return isValid;
    }

    protected boolean validateFilmDuration(Film film) {
        if (film.getDuration().isNegative()) {
            isValid = false;
        }
        if (film.getDuration().isZero()) {
            isValid = false;
        }
        return isValid;
    }

    public void validateUser(User user) throws ValidateException {
        validateUserEmail(user);
        validateUserLogin(user);
        validateUserName(user);
        validateUserBirthday(user);

        if (!isValid) {
            throw new ValidateException("Данные пользователя не прошли валидацию.");
        }

        System.out.println("Данные пользователя прошли валидацию.");
    }

    protected boolean validateUserEmail(User user) {
        if (user.getEmail().isBlank()) {
            isValid = false;
        }
        if (!user.getEmail().contains("@")) {
            isValid = false;
        }
        return isValid;
    }

    protected boolean validateUserLogin(User user) {
        if (user.getLogin().isBlank()) {
            isValid = false;
        }
        if (user.getLogin().contains(" ")) {
            isValid = false;
        }
        return isValid;
    }

    private void validateUserName(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    protected boolean validateUserBirthday(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            isValid = false;
        }
        return isValid;
    }

}
