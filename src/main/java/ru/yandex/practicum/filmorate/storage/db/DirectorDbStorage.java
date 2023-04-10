package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.storage.db.FilmDbStorage.makeFilm;

@Qualifier
@Component
@RequiredArgsConstructor
public class DirectorDbStorage implements DirectorStorage {

    private final JdbcTemplate jdcTemplate;

    @Override
    public Director createDirector(Director director) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdcTemplate)
                .withTableName("directors")
                .usingGeneratedKeyColumns("director_id");

        Integer directorId = simpleJdbcInsert.executeAndReturnKey(toMap(director)).intValue();
        director.setId(directorId);
        return director;
    }

    @Override
    public Director updateDirector(Director director) {
        String sql = "UPDATE directors SET name =? " +
                     "WHERE director_id =?";
        jdcTemplate.update(sql, director.getName(), director.getId());
        return director;
    }

    @Override
    public void deleteDirector(Integer directorId) {
        String sql = "DELETE FROM directors WHERE director_id =?";
        jdcTemplate.update(sql, directorId);
    }

    @Override
    public Director getDirector(Integer id) {
        String sql = "SELECT * FROM directors WHERE director_id =?";
        List<Director> directors = jdcTemplate.query(sql, ((rs, rowNum) -> makeDirector(rs)), id);

        if (directors.size() != 1) {
            throw new NotFoundException("Режиссер с идентификатором: " + id + " не существует.");
        }

        return directors.get(0);
    }

    @Override
    public Iterable<Director> getAllDirectors() {
        String sql = "SELECT * FROM directors";
        return jdcTemplate.query(sql, ((rs, rowNum) -> makeDirector(rs)));
    }

    public List<Film> getDirectorFilms(Integer directorId, String sortBy) {
        String sql;
        if (sortBy.equals("year")) {
            sql =   "SELECT * " +
                    "FROM films WHERE film_id = (SELECT * " +
                                                "FROM director_films " +
                                                "WHERE director_id =? )" +
                    "ORDER BY release_date";
        } else {
            sql =   "SELECT * " +
                    "FROM films WHERE film_id = (SELECT * " +
                                                "FROM director_films " +
                                                "WHERE director_id =? )" +
                    "ORDER BY rate";
        }

        return jdcTemplate.query(sql, ((rs, rowNum) -> makeFilm(rs)), directorId);

    }

    private Map<String, Object> toMap(Director director) {
        Map<String, Object> directorParameters = new HashMap<>();

        directorParameters.put("director_id", director.getId());
        directorParameters.put("name", director.getName());

        return directorParameters;
    }

    private Director makeDirector(ResultSet rs) throws SQLException {
        Director director = new Director();
        director.setId(rs.getInt("director_id"));
        director.setName(rs.getString("name"));
        return director;
    }
}
