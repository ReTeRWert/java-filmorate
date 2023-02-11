package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utilites.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Validator validator = new Validator();


    @GetMapping("/films")
    public List<Film> getFilms() {

        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film, HttpServletRequest request) {

        try {
            validator.validateFilm(film);

            if (films.containsValue(film)) {
                System.out.println("Такой фильм уже есть");
                return null;
            }

            films.put(film.getId(), film);
            log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    request.getMethod(), request.getRequestURI(), request.getQueryString());

        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film, HttpServletRequest request) {
        try {
            validator.validateFilm(film);

            if (films.containsValue(film)) {
                System.out.println("Такой фильм уже есть");
                return null;
            }

            films.replace(film.getId(), film);
            log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    request.getMethod(), request.getRequestURI(), request.getQueryString());

        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }
        return film;
    }
}
