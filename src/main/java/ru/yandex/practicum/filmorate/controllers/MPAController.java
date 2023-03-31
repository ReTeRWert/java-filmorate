package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.mpa.MPAService;


import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MPAController {
    private final MPAService mpaService;

    @GetMapping("/{mpaId}")
    public MPA getById(@PathVariable int mpaId) throws NotFoundException {
        log.debug("Входящий запрос на получение mpa рейтинга по id = {}", mpaId);
        return mpaService.get(mpaId);
    }

    @GetMapping
    public List<MPA> getAll() {
        log.debug("Входящий запрос на получение списка всего рейтинга");
        return mpaService.getAll();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaNotFound(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
