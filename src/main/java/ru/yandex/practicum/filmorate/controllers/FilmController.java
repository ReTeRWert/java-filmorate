package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utilites.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final FilmValidator validator = new FilmValidator();
    private static int id = 1;


    @GetMapping("/films")
    public List<Film> getFilms() {

        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidateException {

        validator.validateFilm(film);

        if (films.containsValue(film)) {
            throw new ValidateException("Такой фильм уже есть");
        }

        film.setId(id);
        films.put(film.getId(), film);
        id++;
        log.info("Выполнено добавление фильма: {}", film);

        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidateException {

        validator.validateFilm(film);

        if (!films.containsKey(film.getId())) {
            throw new ValidateException("Такого фильма еще нет.");
        }

        films.replace(film.getId(), film);
        log.info("Выполнено обновление фильма: {}", film);

        return film;
    }
}
