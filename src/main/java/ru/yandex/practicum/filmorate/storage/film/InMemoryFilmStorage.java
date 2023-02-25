package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utilites.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final FilmValidator validator = new FilmValidator();
    private static int id = 1;


    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        validator.validateFilm(film);

        if (films.containsValue(film)) {
            throw new ValidateException("Такой фильм уже есть");
        }

        film.setId(id);
        films.put(film.getId(), film);
        id++;

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        validator.validateFilm(film);

        if (!films.containsKey(film.getId())) {
            throw new NullPointerException("Такого фильма еще нет.");
        }

        films.replace(film.getId(), film);

        return film;
    }

    public Film getFilmById(Integer id) {
        if (!films.containsKey(id)) {
            throw new NullPointerException("Фильма с id: " + id + " не существует.");
        }
        return films.get(id);
    }
}
