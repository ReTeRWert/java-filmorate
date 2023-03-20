package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;


    private void addFilm() {
        Film film = new Film();
        film.setName("Test film");
        film.setReleaseDate(LocalDate.of(2022, 10, 15));
        film.setDescription("description testFilm");
        film.setDuration(190);
        film.setRate(10);
        film.setMpa(new Mpa(1, "G"));

        filmDbStorage.addFilm(film);
    }

    private void removeFilm() {

        for (int i = 0; i < filmDbStorage.getFilms().size(); i++){
            filmDbStorage.removeFilm(i);
        }
    }

    @Test
    void addFilmAndGetById() {
        addFilm();
        Film film = filmDbStorage.getFilmById(1);

        assertEquals("Test film", film.getName());
        assertEquals(190, film.getDuration());
        assertEquals(10, film.getRate());

        Optional<Film> filmOptional = Optional.of(film);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("id", 1));

        removeFilm();
    }

    @Test
    void updateFilm() {

        Film film = filmDbStorage.getFilmById(1);

        film.setName("Update test film");
        film.setDuration(50);
        film.setRate(5);

        filmDbStorage.updateFilm(film);

        Film updateFilm = filmDbStorage.getFilmById(1);

        assertEquals("Update test film", updateFilm.getName());
        assertEquals(50, updateFilm.getDuration());
        assertEquals(5, updateFilm.getRate());

    }

    @Test
    void getMostPopular() {

        Film film2 = new Film();
        film2.setName("new film");
        film2.setReleaseDate(LocalDate.of(2022, 10, 15));
        film2.setDescription("New description testFilm");
        film2.setDuration(120);
        film2.setRate(7);
        film2.setMpa(new Mpa(1, "G"));

        filmDbStorage.addFilm(film2);

        List<Film> popular = filmDbStorage.getMostPopular(5);

        assertNotNull(popular);
        assertEquals(2, popular.size());
        assertEquals("new film", popular.get(0).getName());
    }

    @Test
    void getFilms() {

        List<Film> films = filmDbStorage.getFilms();

        assertNotNull(films);
        assertEquals(2, films.size());
    }
}
