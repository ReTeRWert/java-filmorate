package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DirectorDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorDbStorage directorDbStorage;

    public Director createDirector(Director director) {

        return directorDbStorage.createDirector(director);
    }

    public void updateDirector(Director director) {
        if (directorDbStorage.getDirector(director.getId()) == null) {
            throw new ValidationException("Такой режиссер не существует.");
        }
        directorDbStorage.updateDirector(director);
    }

    public void deleteDirector(Integer directorId) {
        if (directorDbStorage.getDirector(directorId) == null) {
            throw new ValidationException("Такой режиссер не существует.");
        }
        directorDbStorage.deleteDirector(directorId);
    }

    public Director getDirector(Integer directorId) {
        return directorDbStorage.getDirector(directorId);
    }

    public Iterable<Director> getAllDirectors() {
        return directorDbStorage.getAllDirectors();
    }
}
