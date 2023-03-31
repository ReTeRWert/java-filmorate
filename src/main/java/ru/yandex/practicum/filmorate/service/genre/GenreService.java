package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

/**
 * Класс-сервис, отвечающий за логику работы с жанрами
 */
@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getById(int genreId) throws NotFoundException {
        final Genre genre = genreStorage.getById(genreId);
        if (genre == null) {
            throw new NotFoundException("Genre with id = " + genreId + " not found");
        }
        return genre;
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }
}