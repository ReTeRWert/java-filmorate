package ru.yandex.practicum.filmorate.storage.ratingmpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface RatingMpaStorage {

    Mpa getRatingById(Integer id);

    List<Mpa> getRatings();
}