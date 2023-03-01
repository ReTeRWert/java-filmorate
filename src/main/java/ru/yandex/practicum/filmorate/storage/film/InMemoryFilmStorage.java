package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    private long lastId = 0L;

    @Override
    public Film create(Film film) {
        film.setId(++lastId);
        films.put(lastId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(long id) {
        films.remove(id);
    }

    @Override
    public Film getById(long id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public void addLike(long id, long userId) {
        films.get(id).getLikes().add(userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        films.get(id).getLikes().remove(userId);
    }

    @Override
    public Collection<Film> getTopFilm(int count) {
        return null;
    }

}
