package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;

public interface DirectorStorage {
    Director createDirector(Director director);

    Director updateDirector(Director director);

    void deleteDirector(Integer directorId);

    Director getDirector(Integer id);

    Iterable<Director> getAllDirectors();
}
