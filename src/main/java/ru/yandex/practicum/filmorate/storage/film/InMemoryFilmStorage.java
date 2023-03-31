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

    @Override
    public Film create(Film film) {
        Long generatedId = Film.setIdCounter();
        film.setId(generatedId);
        films.put(generatedId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public void remove(Long id) {
        films.remove(id);
    }



    @Override
    public Film getById(Long id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public void deleteAll() {

    }
}
