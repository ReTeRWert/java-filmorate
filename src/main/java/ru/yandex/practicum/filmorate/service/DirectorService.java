package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.db.DirectorDbStorage;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorDbStorage directorDbStorage;

    public void createDirector(Director director) {
        if (directorDbStorage.getDirector(director.getId()) != null) {
            throw new ValidateException("Такой режиссер уже существует.");
        }

        directorDbStorage.createDirector(director);
    }

    public void updateDirector(Director director) {
        if (directorDbStorage.getDirector(director.getId()) == null) {
            throw new ValidateException("Такой режиссер не существует.");
        }
        directorDbStorage.updateDirector(director);
    }

    public void deleteDirector(Integer directorId) {
        if (directorDbStorage.getDirector(directorId) == null) {
            throw new ValidateException("Такой режиссер не существует.");
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