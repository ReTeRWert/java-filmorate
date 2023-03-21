package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Qualifier
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmGenresDbStorage filmGenresDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        Integer filmId = jdbcInsert.executeAndReturnKey(toMap(film)).intValue();
        film.setId(filmId);
        filmGenresDbStorage.updateGenresByFilm(film);
        updateRate(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE films " +
                "SET film_name = ?, film_description = ?, release_date =?, duration = ?, rating_id =? " +
                "WHERE film_id =?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        filmGenresDbStorage.updateGenresByFilm(film);
        updateRate(film);

        if (film.getRate() != null) {
            sql = "update films set rate = ? where film_id = ?";
            jdbcTemplate.update(sql, film.getRate(), film.getId());
        }

        return film;
    }

    // если честно я не очень представляю как тут одним запросом можно собрать сразу фильм со списком жанров. Структура
    // такая, что каждому фильму соответствует одно значение. А вот уже жанров может быть несколько, поэтому для них
    // создана отдельная таблица, и как их совместить в одной вообще непонятно. Сейчас фильмы собираются отдельно, жанры
    // отдельно, а потом собираются в единый объект.

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT * " +
                "FROM films AS f " +
                "JOIN rating_mpa AS r ON f.rating_id = r.rating_id ";

        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        setGenresInFilms(films);
        return films;
    }

    public Film getFilmById(Integer filmId) {
        String sql = "SELECT * " +
                "FROM films AS f, rating_mpa AS r " +
                "WHERE f.rating_id = r.rating_id AND f.film_id = ?";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), filmId);
        if (films.size() != 1) {
            throw new NotFoundException("Фильма с таким id не существует.");
        }
        setGenresInFilms(films);
        return films.get(0);
    }

    @Override
    public List<Film> getMostPopular(Integer lim) {
        final String sql = "SELECT * " +
                "FROM films AS f, rating_mpa AS m " +
                "WHERE f.rating_id = m.rating_id " +
                "ORDER BY rate DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), lim);
    }

    public void removeFilm(Integer filmId) {
        String sql = "DELETE FROM films " +
                "WHERE film_id =?";
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

    public void updateRate(Film film) {
        String sql = "SELECT COUNT(user_id) " +
                "FROM likes " +
                "WHERE film_id = ?";
        Integer filmRate = jdbcTemplate.queryForObject(sql, Integer.class, film.getId());
        sql = "UPDATE films " +
                "SET rate = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql, (filmRate + film.getRate()), film.getId());
    }

    private void setGenresInFilms(List<Film> films) {
        String forInSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));

        final String sql = "SELECT * " +
                "FROM film_genres AS fg, genres AS g " +
                "WHERE fg.genre_id = g.genre_id AND fg.film_id IN (" + forInSql + ")";

        jdbcTemplate.query(sql, (rs) -> {
            final Film film = filmById.get(rs.getInt("film_id"));
            film.addGenre(genreDbStorage.makeGenre(rs));
        }, films.stream().map(Film::getId).toArray());
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