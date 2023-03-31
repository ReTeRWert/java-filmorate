package ru.yandex.practicum.filmorate.storage.filmlikes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmComparator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class InMemoryFilmLikesStorage implements FilmLikesStorage {
    private final FilmStorage filmStorage;

    public InMemoryFilmLikesStorage(@Qualifier("inMemoryFilmStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    @Override
    public void saveLike(Long filmId, Long userId) {
        filmStorage.getById(filmId).getLikes().add(userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        filmStorage.getById(filmId).getLikes().remove(userId);
    }

    @Override
    public List<Film> getCount(int count) {
        List<Film> films = (List<Film>) filmStorage.getAll();
        films.sort(new FilmComparator());
        return films.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public List<Film> getCommonFilms(long userId, long friendId) {
        return new ArrayList<>();
    }
}