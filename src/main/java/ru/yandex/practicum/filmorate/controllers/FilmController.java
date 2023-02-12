package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@RestController
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    long lastId = 0;

    HashMap<Long, Film> films = new HashMap();

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.info("Запрошен список фильмов");
        return films.values();
    }

    @PostMapping("/film")
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(++lastId);
        log.debug("Добавлен фильм: {}", film);
        return films.put(film.getId(), film);
    }

    @PutMapping("/film")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма");
        return films.put(film.getId(), film);
    }
}
