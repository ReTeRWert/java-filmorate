package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.filmgenres.FilmGenresDbStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmGenresDbStorage filmGenresDbStorage;
    private final GenreDbStorage genreDbStorage;
    private final LikeDbStorage likeDbStorage;


    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        Integer filmId = jdbcInsert.executeAndReturnKey(toMap(film)).intValue();
        film.setId(filmId);
        filmGenresDbStorage.updateGenresByFilm(film);
        likeDbStorage.updateRate(film);
        return film;
    }


    @Override
    public Film updateFilm(Film film) {
        String sql = "update films set film_name = ?, film_description = ?, release_date =?, duration = ?," +
                "rating_id =? where film_id =?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        filmGenresDbStorage.updateGenresByFilm(film);
        likeDbStorage.updateRate(film);

        if (film.getRate() != null) {
            sql = "update films set rate = ? where film_id = ?";
            jdbcTemplate.update(sql, film.getRate(), film.getId());
        }

        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "select * from films as f join rating_mpa as r ON f.rating_id = r.rating_id";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        genreDbStorage.addGenresForFilms(films);
        return films;
    }

    public Film getFilmById(Integer filmId) {
        String sql = "select * from films as f, rating_mpa as r where f.rating_id = r.rating_id and f.film_id = ?";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), filmId);
        if (films.size() != 1) {
            throw new NotFoundException("Фильма с таким id не существует.");
        }
        genreDbStorage.addGenresForFilms(films);
        return films.get(0);
    }

    public void removeFilm(Integer filmId) {
        String sql = "delete from films where film_id =?";
        jdbcTemplate.update(sql, filmId);
    }

    public static Film makeFilm(ResultSet rs) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("film_name"),
                rs.getString("film_description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                new Mpa(rs.getInt("rating_id"), rs.getString("rating_name")),
                rs.getInt(("rate"))
        );
    }

    private Map<String, Object> toMap(Film film) {
        Map<String, Object> filmParameters = new HashMap<>();
        filmParameters.put("film_name", film.getName());
        filmParameters.put("film_description", film.getDescription());
        filmParameters.put("release_date", film.getReleaseDate());
        filmParameters.put("duration", film.getDuration());
        filmParameters.put("rating_id", film.getMpa().getId());
        filmParameters.put("rate", film.getRate());

        return filmParameters;
    }
}