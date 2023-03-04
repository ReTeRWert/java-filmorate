package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;


@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    @Autowired
    private final UserStorage userStorage;

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        if (filmStorage.getById(film.getId()) != null) {
            return filmStorage.update(film);
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public void delete(Long id) {
        if (filmStorage.getById(id) != null) {
            filmStorage.delete(id);
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Collection<Film> getAll() {
        return filmStorage.getFilms();
    }

    public Film addLike(Long id, Long userId) {
        if (userStorage.get(userId) != null && filmStorage.getById(id) != null) {
            return filmStorage.addLike(id, userId);
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Film deleteLike(Long id, Long userId) {
        if (userStorage.get(userId) != null && filmStorage.getById(id) != null) {
            return filmStorage.deleteLike(id, userId);
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Collection<Film> getPopularFilm(int count) {
        return filmStorage.getPorularFilm(count);
    }
}
