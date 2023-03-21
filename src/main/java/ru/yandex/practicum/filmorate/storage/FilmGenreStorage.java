package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreStorage {

    void updateGenresByFilm(Film film);

    void removeGenreFromFilm(Integer filmId, Integer genreId);

    List<Genre> getGenresByFilm(Integer filmId);

}
