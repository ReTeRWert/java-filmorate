package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

@RestController
@RequestMapping("/directors")
@Slf4j
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping
    public Director createDirector(@RequestBody Director director) {
        directorService.createDirector(director);
        log.info("Создан режиссер: {}", director);
        return director;
    }

    @PutMapping()
    public Director updateDirector(@RequestBody Director director) {
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
