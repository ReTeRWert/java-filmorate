package ru.yandex.practicum.filmorate.storage.filmlikes;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;


public interface FilmLikesStorage {
    void saveLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    List<Film> getCount(int count);

    List<Film> getCommonFilms(long userId, long friendId);

}