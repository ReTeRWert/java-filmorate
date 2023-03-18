package ru.yandex.practicum.filmorate.storage.ratingmpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Qualifier
@Component
@RequiredArgsConstructor
public class MpaDbStorage implements RatingMpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getRatingById(Integer id) {
        String sql = "SELECT * " +
                "FROM rating_mpa " +
                "WHERE rating_id = ?";

        List<Mpa> mpas = jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs), id);

        if (mpas.size() != 1) {
            throw new NotFoundException("Такого рейтинга не существует.");
        }
        return mpas.get(0);
    }

    private Mpa makeRating(ResultSet rs) throws SQLException {
        return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
    }

    @Override
    public List<Mpa> getRatings() {
        String sql = "SELECT * " +
                "FROM rating_mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs));
    }
}
