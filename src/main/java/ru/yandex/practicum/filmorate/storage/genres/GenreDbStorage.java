package ru.yandex.practicum.filmorate.storage.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Qualifier
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;



    @Override
    public Genre getGenreById(int id) {
        String sql = "select * from genres where genre_id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);
        if(genres.size() != 1) {
            throw new NotFoundException("Жанра с таким идентификатором не существует.");
        }
        return genres.get(0);
    }

    @Override
    public List<Genre> getGenres() {
        String sql = "select * from genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public void addGenresForFilms(List<Film> films) {
        String forInSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));
        final String sql = "SELECT * FROM film_genres AS fg, genres AS g " +
                "WHERE fg.genre_id = g.genre_id and fg.film_id IN (" + forInSql + ")";
        jdbcTemplate.query(sql, (rs) -> {
            final Film film = filmById.get(rs.getInt("film_id"));
            film.addGenre(makeGenre(rs));
        }, films.stream().map(Film::getId).toArray());
    }

    public Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}
