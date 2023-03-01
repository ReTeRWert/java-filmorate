package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException("Фильма с таким ID нет в базе");
        }
        return film;
    }

    @Override
    public void delete(long id) {
        if (films.remove(id) == null) {
            throw new NotFoundException("Фильма с таким ID нет в базе");
        }
    }

    @Override
    public Film getById(long id) {
        Film film = films.get(id);
        if (film != null) {
            return film;
        } else {
            throw new NotFoundException("Фильма с таким ID нет в базе");
        }
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
    public Collection<Film> getPorularFilm(Integer count) {
        try {
            if (count == null || count < 0) {count = 10;}
            return films.values().stream().sorted(((o1, o2) -> (o2.getLikes().size() - o1.getLikes().size()))).limit(count).collect(Collectors.toList());
        } catch (RuntimeException e){
            throw new RuntimeException("Ошибка сервера");
        }
    }
}
