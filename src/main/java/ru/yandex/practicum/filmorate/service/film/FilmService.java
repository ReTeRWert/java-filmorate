package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;
    private long lastId = 0L;

    public Film create(Film film) {
        film.setId(++lastId);
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (filmStorage.getById(film.getId()) != null) {
            return filmStorage.add(film);
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

    public Collection<Film> getAllFilms() {
        return filmStorage.getFilms();
    }

    public Film getById(long id) {
        Film film = filmStorage.getById(id);
        if (film != null) {
            return film;
        } else {
            throw new NotFoundException("Фильм не найден");
        }
    }

    public Film addLike(Long id, Long userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.get(userId);
        if (user != null && film != null) {
            film.getLikes().add(userId);
            return film;
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Film deleteLike(Long id, Long userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.get(userId);
        if (user != null && film != null) {
            film.getLikes().remove(userId);
            return film;
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Collection<Film> getPopularFilm(int count) {
        try {
            if (count < 0) {
                count = 10;
            }
            return filmStorage.getFilms().stream().sorted(((o1, o2) -> (o2.getLikes().size() - o1.getLikes().size()))).limit(count).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка сервера");
        }
    }
}
