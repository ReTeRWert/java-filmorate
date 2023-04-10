package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.DirectorDbStorage;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final DirectorDbStorage directorDbStorage;


    public void addFilm(Film film) {
        if (filmDbStorage.getFilms().contains(film)) {
            throw new ValidateException("Такой фильм уже есть.");
        }

        filmDbStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        checkFilm(film.getId());

        filmDbStorage.updateFilm(film);
    }

    public Film getFilmById(Integer filmId) {
        checkFilm(filmId);
        return filmDbStorage.getFilmById(filmId);
    }

    public List<Film> getFilms() {
        return filmDbStorage.getFilms();
    }

    public List<Film> getMostPopularFilms(Integer limit) {
        return filmDbStorage.getMostPopular(limit);
    }

    public void removeFilmById(Integer filmId) {
        checkFilm(filmId);
        filmDbStorage.removeFilm(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        checkFilm(filmId);
        checkUser(userId);

        likeDbStorage.addLike(filmId, userId);
        filmDbStorage.updateRate(filmDbStorage.getFilmById(filmId));
    }

    public void removeLike(Integer filmId, Integer userId) {
        checkFilm(filmId);
        checkUser(userId);

        likeDbStorage.removeLike(filmId, userId);
        filmDbStorage.updateRate(filmDbStorage.getFilmById(filmId));
    }

    public List<Film> getCommonFilms(Integer userId, Integer friendId) {
        checkUser(userId);
        checkUser(friendId);

        return userDbStorage.getCommonFilms(userId, friendId);
    }

    public List<Film> getDirectorFilms(Integer directorId, String sortBy) {
       return directorDbStorage.getDirectorFilms(directorId, sortBy);
    }
    private void checkFilm(Integer filmId) {
        if (filmDbStorage.getFilmById(filmId) == null) {
            throw new NotFoundException("Фильма с id: " + filmId + " не существует.");
        }
    }

    private void checkUser(Integer userId) {
        if (userDbStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователя с id: " + userId + " не существует");
        }
    }


}
