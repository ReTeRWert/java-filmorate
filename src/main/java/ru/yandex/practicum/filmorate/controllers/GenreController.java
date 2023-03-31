package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;


import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;
    
    @GetMapping("/{genreId}")
    public Genre getById(@PathVariable int genreId) throws NotFoundException {
        log.debug("Входящий запрос на получение жанра по id = {}", genreId);
        return genreService.getById(genreId);
    }

    @GetMapping
    public List<Genre> getAll() {
        log.debug("Входящий запрос на получение списка всех жанров");
        return genreService.getAll();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenreNotFound(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
