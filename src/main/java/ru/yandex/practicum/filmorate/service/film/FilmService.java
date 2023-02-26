package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utilites.FilmComparator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId)
                .getLikes()
                .add(userStorage.getUserById(userId)
                        .getEmail());
    }

    public void removeLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId)
                .getLikes()
                .remove(userStorage.getUserById(userId)
                        .getEmail());
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public List<Film> getMostPopularFilms(Integer count) {

        return filmStorage.getFilms().stream()
                .sorted(new FilmComparator())
                .limit(count)
                .collect(Collectors.toList());
    }

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }
}
