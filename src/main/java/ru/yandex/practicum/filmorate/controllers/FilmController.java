package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.utilites.FilmValidator;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;
    private final FilmValidator validator;

    @GetMapping("/films")
    public List<Film> getFilms() {
        return service.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return service.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getMostPopular(@RequestParam(defaultValue = "10") Integer count) {
        return service.getMostPopularFilms(count);
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidateException {
        validator.validateFilm(film);
        service.addFilm(film);
        log.info("Выполнено добавление фильма: {}", film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidateException {
        validator.validateFilm(film);
        service.updateFilm(film);
        log.info("Выполнено обновление фильма: {}", film);
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable(value = "id") Integer filmId, @PathVariable Integer userId) {
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLikeFilm(@PathVariable(value = "id") Integer filmId, @PathVariable Integer userId) {
        service.removeLike(filmId, userId);
    }


}
