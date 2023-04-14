package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;
import ru.yandex.practicum.filmorate.validator.DirectorValidator;

@RestController
@RequestMapping("/directors")
@Slf4j
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;
    private final DirectorValidator directorValidator;

    @PostMapping
    public Director create(@Validated @RequestBody Director director) {
        directorValidator.validate(director);
        return directorService.createDirector(director);
    }

    @PutMapping
    public Director updateDirector(@RequestBody Director director) {
        directorValidator.validate(director);
        directorService.updateDirector(director);
        log.info("Обновлен режиссер: {}", director);
        return director;
    }

    @DeleteMapping("/{directorId}")
    public void deleteDirector(@PathVariable Integer directorId) {
        directorService.deleteDirector(directorId);
        log.info("Удален режиссер: {}", directorId);
    }

    @GetMapping("/{directorId}")
    public Director getDirectorById(@PathVariable Integer directorId) {
        return directorService.getDirector(directorId);
    }

    @GetMapping
    public Iterable<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }
}
