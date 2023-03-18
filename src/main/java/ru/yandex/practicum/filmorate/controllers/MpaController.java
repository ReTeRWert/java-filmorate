package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MpaController {

    private final MpaService ratingService;

    @GetMapping("/mpa")
    public List<Mpa> getRatings() {
        return ratingService.getRatings();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getRatingById(@PathVariable Integer id) {
        return ratingService.getRatingById(id);
    }
}
