package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DirectorStorage {
    Director createDirector(Director director);

    Director updateDirector(Director director);

    void deleteDirector(Integer directorId);

    Director getDirector(Integer id);

    Iterable<Director> getAllDirectors();

    List<Director> findDirectorsByFilm(long filmId);

    List<Film> getDirectorFilms(Integer directorId, String sortBy);
}
