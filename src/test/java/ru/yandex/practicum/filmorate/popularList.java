package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"/schema.sql", "/data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class popularList {

    private final FilmService filmService;
    private final UserService userService;
    private final Film film = new Film(1L, "Покемон фильм первый: Мьюту против Мью",
            "Создан первый искусственный покемон",
            LocalDate.of(1998, 12, 22), 90, 2, null,new MPA(2, "PG"));

    film2 = new Film.FilmBuilder()
            .id(rs.getLong("film_id"))
//            .name(rs.getString("name"))
//            .description(rs.getString("description"))
//            .releaseDate(rs.getDate("release_date").toLocalDate())
//            .duration(rs.getLong("duration"))
//            .rate(rs.getInt("rate"))
//            .genres(genreList)
//                .mpa(mpaStorage.findMPAById(rs.getInt("age_id")))
//            .build();
    @Test
    public void addAndGetFilmTest() {
        filmService.create(film);
        assertEquals(film, filmService.findFilmById(film.getId()));
    }
//    private final User user = new User(1, "testMail", "testLogin", "testName",
//            LocalDate.of(1995, 4, 13));
//
//    film2 = new Film.FilmBuilder()
//            .id(2L)
//                .name("Покемон фильм первый: Мьюту против Мью")
//                .description("Создан первый искусственный покемон")
//                .releaseDate(LocalDate.of(1998, 12, 22))
//            .duration(90L)
//                .mpa(new MPA(2, "PG"))
//            .genres(Arrays.asList(new Genre(3, "Мультфильм")))
//            .build();

}
