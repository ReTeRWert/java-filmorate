package ru.yandex.practicum.filmorate.storage.filmgenres;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreDbStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Qualifier
@Component
@RequiredArgsConstructor
public class FilmGenresDbStorage implements FilmGenreStorage {

    private final GenreDbStorage genreDbStorage;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public void updateGenresByFilm(Film film) {
        checkGenres(film);
        String sql = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(sql, film.getId());

        sql = "insert into film_genres (film_id, genre_id) values (?,?)";
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
        String sql = "delete from film_genres where film_id =? and genre_id =?";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public List<Genre> getGenresByFilm(Integer filmId) {
        String sql = "select genre_name from genres where genres.genre_id IN (select * from film_genres where film_id = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreDbStorage.makeGenre(rs), filmId);
    }

    private void checkGenres(Film film) {
        Set<Genre> genres = new LinkedHashSet<>(film.getGenres());
        film.setGenres(new ArrayList<>(genres));
    }
}
