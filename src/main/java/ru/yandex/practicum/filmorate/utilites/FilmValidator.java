package ru.yandex.practicum.filmorate.utilites;

import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
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
        return !film.getName().isBlank() && film.getName() != null;
    }

    protected boolean validateFilmDescription(Film film) {
        return film.getDescription().length() <= 200 && film.getDescription() != null;
    }

    protected boolean validateFilmReleaseDate(Film film) {
        return !film.getReleaseDate().isBefore(movieBirthday) && film.getReleaseDate() != null;
    }

    protected boolean validateFilmDuration(Film film) {
        if (film.getDuration() == null) {
            return false;
        } else if (film.getDuration() < 0) {
            return false;
        } else return film.getDuration() != 0;
    }
}
