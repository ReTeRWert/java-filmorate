package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    void delete(long id);

    Film getById(long id);

    Collection<Film> getFilms();

    void addLike(long id, long userId);

    void deleteLike(long id, long userId);

    Collection<Film> getTopFilm(int count);
}
