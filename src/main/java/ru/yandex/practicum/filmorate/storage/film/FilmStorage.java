package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    void delete(Long id);

    Film getById(Long id);

    Collection<Film> getFilms();

    Film addLike(Long id, Long userId);

    Film deleteLike(Long id, Long userId);

    Collection<Film> getPorularFilm(int count);


}
