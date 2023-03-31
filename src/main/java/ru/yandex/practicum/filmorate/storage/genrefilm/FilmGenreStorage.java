package ru.yandex.practicum.filmorate.storage.genrefilm;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;
import java.util.TreeSet;

public interface FilmGenreStorage {
    void create(Long filmId, int genreId);

    TreeSet<Integer> getByFilmId(Long filmId);
    void deleteByFilmId(Long filmId);

    Set<Genre> getGenreList(Long filmId);
}