package ru.yandex.practicum.filmorate.utilites;

import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    private static final LocalDate movieBirthday = LocalDate.of(1895, 12, 28);


    public void validateFilm(Film film) throws ValidateException {
        if (!validateFilmName(film)) {
            throw new ValidateException("Имя не может быть пустым.");
        }
        if (!validateFilmDescription(film)) {
            throw new ValidateException("Описание фильма не может быть больше 200 символов.");
        }
        if (!validateFilmReleaseDate(film)) {
            throw new ValidateException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
        if (!validateFilmDuration(film)) {
            throw new ValidateException("Длительность фильма не может быть отрицательной или равна 0.");
        }
        System.out.println("Фильм прошел валидацию.");
    }

    protected boolean validateFilmName(Film film) {
        return !film.getName().isBlank();
    }

    protected boolean validateFilmDescription(Film film) {
        return film.getDescription().length() <= 200;
    }

    protected boolean validateFilmReleaseDate(Film film) {
        return !film.getReleaseDate().isBefore(movieBirthday);
    }

    protected boolean validateFilmDuration(Film film) {
        if (film.getDuration() < 0) {
            return false;
        }
        return !(film.getDuration() == 0);
    }



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
        if (user.getEmail().isBlank()) {
            return false;
        }
        return user.getEmail().contains("@");
    }

    protected boolean validateUserLogin(User user) {
        if (user.getLogin().isBlank()) {
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
        return !user.getBirthday().isAfter(LocalDate.now());
    }

}
