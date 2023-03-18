package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void addLike(int filmId, int userId) {
        String sql = "insert into likes (user_id, film_id) values (?,?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql,filmId, userId);
    }

    @Override
    public List<Film> getMostPopular(Integer lim) {
        final String sql = "SELECT * FROM films as f, rating_mpa as m where f.rating_id = m.rating_id" +
                " ORDER BY rate DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), lim);
    }

    public Film makeFilm(ResultSet rs) throws SQLException {
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
        String sql = "select count(user_id) from likes where film_id = ?";
        Integer filmRate = jdbcTemplate.queryForObject(sql, Integer.class, film.getId());
        sql = "update films set rate = ? where film_id = ?";
        jdbcTemplate.update(sql, (filmRate + film.getRate()), film.getId());
    }
}
