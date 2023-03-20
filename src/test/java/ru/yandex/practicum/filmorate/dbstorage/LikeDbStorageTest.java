package ru.yandex.practicum.filmorate.dbstorage;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.db.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorageTest {

    private final LikeDbStorage likeDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;


    private void addFilmAndUser() {
        Film film = new Film();
        film.setName("Test film");
        film.setReleaseDate(LocalDate.of(2022, 10, 15));
        film.setDescription("description testFilm");
        film.setDuration(190);
        film.setRate(10);
        film.setMpa(new Mpa(1, "G"));

        filmDbStorage.addFilm(film);




    }

    private void removeFriendAndUser() {
        filmDbStorage.removeFilm(1);
        userDbStorage.removeUserById(1);
    }

    @Test
    void addLikeAndRemoveLike() {
        addFilmAndUser();
        likeDbStorage.addLike(1,1);

        String sql = "SELECT COUNT(user_id) " +
                "FROM likes " +
                "WHERE film_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, 1);

        assertEquals(1, count);

        likeDbStorage.removeLike(1,1);

        sql = "SELECT COUNT(user_id) " +
                "FROM likes " +
                "WHERE film_id = ?";
        count = jdbcTemplate.queryForObject(sql, Integer.class, 1);

        assertEquals(0, count);
    }
}
