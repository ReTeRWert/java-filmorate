package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Qualifier
@Component
@RequiredArgsConstructor
public class FilmGenresDbStorage implements FilmGenreStorage {

    private final GenreDbStorage genreDbStorage;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public void updateGenresByFilm(Film film) {
        checkGenres(film);
        String sql = "DELETE FROM film_genres " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql, film.getId());

        sql = "INSERT INTO film_genres(film_id, genre_id) " +
                "VALUES (?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, film.getGenres().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }

    @Override
    public void removeGenreFromFilm(Integer filmId, Integer genreId) {
        String sql = "DELETE FROM film_genres " +
                "WHERE film_id =? AND genre_id =?";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public List<Genre> getGenresByFilm(Integer filmId) {
        String sql = "SELECT genre_name " +
                "FROM genres " +
                "WHERE genres.genre_id IN (SELECT * " +
                "FROM film_genres " +
                "WHERE film_id = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreDbStorage.makeGenre(rs), filmId);
    }

    private void checkGenres(Film film) {
        Set<Genre> genres = new LinkedHashSet<>(film.getGenres());
        film.setGenres(new ArrayList<>(genres));
    }
}
