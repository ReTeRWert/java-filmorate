package ru.yandex.practicum.filmorate.storage.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films;
    private static int id = 1;


    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {

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

        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Такого фильма еще нет.");
        }

        films.replace(film.getId(), film);

        return film;
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        return null;
    }

    public Film getFilmById(Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильма с id: " + id + " не существует.");
        }
        return films.get(id);
    }
}
