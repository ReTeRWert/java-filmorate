package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;


@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void delete(long id){
        filmStorage.delete(id);
    }

    public Collection<Film> getAll() {
        return filmStorage.getFilms();
    }

    public void addLike(long id, long userId) {
        filmStorage.addLike(id,userId);
    }

    public void deleteLike(long id, long userId){
        filmStorage.deleteLike(id,userId);
    }

    public Collection<Film> getPopularFilm(Integer count){
        return filmStorage.getPorularFilm(count);
    }
}
