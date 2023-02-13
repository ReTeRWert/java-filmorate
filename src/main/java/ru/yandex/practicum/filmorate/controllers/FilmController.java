package ru.yandex.practicum.filmorate.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.HashMap;

@RestController
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private long lastId = 0;

    private final HashMap<Long, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.info("Запрошен список фильмов");
        return films.values();
    }

    @PostMapping("/films")
    public Film createFilm(@Validated @RequestBody Film film) {
        film.setId(++lastId);
        log.debug("Добавлен фильм: {}", film);
        Film s = films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Validated @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(String.format("%s is not registered", film));
        }
        log.info("Обновление фильма: {}", films.put(film.getId(), film));
        return film;
    }
}
