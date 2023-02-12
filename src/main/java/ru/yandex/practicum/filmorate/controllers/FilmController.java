package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utilites.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final Validator validator = new Validator();
    private static int id = 1;


    @GetMapping("/films")
    public List<Film> getFilms() {

        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film, HttpServletRequest request) throws ValidateException {

        validator.validateFilm(film);

        if (films.containsValue(film)) {
            System.out.println("Такой фильм уже есть");
            return null;
        }

        film.setId(id);
        films.put(film.getId(), film);
        id++;
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());

        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film, HttpServletRequest request) throws ValidateException {

        validator.validateFilm(film);

        if (!films.containsKey(film.getId())) {
            throw new ValidateException("Такого фильма еще нет.");
        }

        films.replace(film.getId(), film);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());


        return film;
    }
}
