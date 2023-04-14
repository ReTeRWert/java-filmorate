package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreStorage {

    public final JdbcTemplate jdbcTemplate;
    private final List<Genre> genreList = new ArrayList<>();

    @Autowired
    public GenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        String sql = "SELECT * FROM Genre";
        genreList.addAll(jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(rs.getInt("genre_id"),
                rs.getString("name"))));
    }

    public Genre findGenreById(int id) {
        return genreList.stream().filter(genre -> genre.getId() == id).findFirst().orElse(null);
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public List<Genre> getFilmGenres(long filmId) {
        String sql = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM FilmGenre WHERE film_id = ?) " +
                " ORDER BY genre_id;";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("name")), filmId);
    }
}