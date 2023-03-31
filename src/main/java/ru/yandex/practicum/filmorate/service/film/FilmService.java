package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.filmlikes.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.genrefilm.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmLikesStorage filmLikesStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final GenreStorage genreStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       @Qualifier("filmLikesDbStorage") FilmLikesStorage filmLikesStorage,
                       FilmGenreStorage filmGenreStorage, GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.filmLikesStorage = filmLikesStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.genreStorage = genreStorage;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

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
            filmStorage.remove(id);
        } else {
            throw new NotFoundException("Данные не найдены");
        }
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
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
            return filmStorage.getAll().stream().sorted(((o1, o2) -> (o2.getLikes().size() - o1.getLikes().size()))).limit(count).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка сервера");
        }
    }
}
