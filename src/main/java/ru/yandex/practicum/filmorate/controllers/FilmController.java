package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.utilites.FilmValidator;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;
    private final FilmValidator validator;

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidateException {
        validator.validateFilm(film);
        service.addFilm(film);
        log.info("Выполнено добавление фильма: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidateException {
        validator.validateFilm(film);
        service.updateFilm(film);
        log.info("Выполнено обновление фильма: {}", film);
        return film;
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable Integer filmId) {
        service.removeFilmById(filmId);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return service.getFilmById(id);
    }

    @GetMapping
    public List<Film> getFilms() {
        return service.getFilms();
    }

    @GetMapping("/popular")
    public List<Film> getMostPopular(@RequestParam(defaultValue = "10") Integer count) {
        return service.getMostPopularFilms(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable(value = "id") Integer filmId, @PathVariable Integer userId) {
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFilm(@PathVariable(value = "id") Integer filmId, @PathVariable Integer userId) {
        service.removeLike(filmId, userId);
    }

    @GetMapping("/common?userId={userId}&friendId={friendId}")
    public void getCommonFilms(@RequestParam(value = "userId") Integer userId,
                               @RequestParam(value = "friendId") Integer friendId) {
        service.getCommonFilms(userId, friendId);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getDirectorFilms(@PathVariable Integer directorId,
                                        @RequestParam(defaultValue = "likes", required = false) String sortBy) {

        if (!(sortBy.equals("year") || sortBy.equals("likes"))) {
            throw new IllegalArgumentException("Сортировка возможна либо по годам, либо по количеству лайков");
        }

        return service.getDirectorFilms(directorId, sortBy);
    }
}
