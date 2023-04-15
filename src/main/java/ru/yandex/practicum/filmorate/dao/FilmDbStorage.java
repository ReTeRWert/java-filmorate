package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final DirectorStorage directorStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rate, f.age_id, " + "GROUP_CONCAT(DISTINCT g.genre_id ORDER BY g.genre_id ASC SEPARATOR ',') AS genre_ids " + "FROM Film AS f " + "LEFT JOIN FilmGenre AS fg ON f.film_id = fg.film_id " + "LEFT JOIN Genre AS g ON fg.genre_id = g.genre_id " + "GROUP BY f.film_id " + "ORDER BY f.film_id ASC";
        List<Film> films = new ArrayList<>();
        Map<Long, List<Genre>> genreMap = new HashMap<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

        while (rs.next()) {
            long filmId = rs.getLong("film_id");
            List<Genre> genres = new ArrayList<>();
            String genreIdsString = rs.getString("genre_ids");

            if (genreIdsString != null) {
                String[] genreIds = genreIdsString.split(",");
                for (String genreId : genreIds) {
                    genres.add(genreStorage.findGenreById(Integer.parseInt(genreId)));
                }
            }
            genreMap.put(filmId, genres);

            Film film = Film.builder().id(filmId).name(rs.getString("name")).description(rs.getString("description")).releaseDate(Objects.requireNonNull(rs.getDate("release_date")).toLocalDate()).duration(rs.getLong("duration")).rate(rs.getInt("rate")).mpa(mpaStorage.findMPAById(rs.getInt("age_id"))).directors(directorStorage.findDirectorsByFilm(filmId)).build();
            films.add(film);
        }
        for (Film film : films) {
            film.setGenres(genreMap.get(film.getId()));
        }
        return films;
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Film").usingGeneratedKeyColumns("film_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", film.getName());
        parameters.put("description", film.getDescription());
        parameters.put("release_date", film.getReleaseDate());
        parameters.put("duration", film.getDuration());
        parameters.put("rate", film.getRate());
        parameters.put("age_id", film.getMpa().getId());


        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        film.setId(key.longValue());

        if (film.getGenres() != null) {
            String sql = "INSERT INTO FilmGenre (film_id, genre_id) VALUES (?, ?)";
            film.getGenres().forEach(genre -> jdbcTemplate.update(sql, film.getId(), genre.getId()));
        } else {
            film.setGenres(new ArrayList<>());
        }

        updateDirectors(film);
        return findFilmById(film.getId());
    }

    private void updateDirectors(Film film) {
        if (film.getDirectors() == null) {
            film.setDirectors(new ArrayList<>());
        }
        String sql = "DELETE FROM director_films " + "WHERE film_id =?";
        jdbcTemplate.update(sql, film.getId());

        sql = "INSERT INTO director_films (film_id, director_id) " + "VALUES (?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, film.getId());
                ps.setLong(2, film.getDirectors().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getDirectors().size();
            }
        });
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlId = "SELECT film_id " + "FROM Film " + "ORDER BY film_id DESC " + "LIMIT 1";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlId);
        sqlRowSet.next();

        if (film.getId() > sqlRowSet.getInt("film_id") || film.getId() <= 0) {
            throw new NotFoundException("Фильм не найден.");
        } else {
            String sql = "UPDATE Film " + "SET name=?, description=?, release_date=?, duration=?, rate=?, age_id=? " + "WHERE film_id=?";
            jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate(), film.getMpa().getId(), film.getId());

            if (film.getGenres() != null) {
                Set<Integer> updatedGenreIds = new HashSet<>();
                film.getGenres().forEach(g -> updatedGenreIds.add(g.getId()));

                Set<Integer> existingGenreIds = new HashSet<>();
                String sqlGenreIds = "SELECT genre_id  " + "FROM FilmGenre " + "WHERE film_id=?";
                SqlRowSet rsGenreIds = jdbcTemplate.queryForRowSet(sqlGenreIds, film.getId());

                while (rsGenreIds.next()) {
                    existingGenreIds.add(rsGenreIds.getInt("genre_id"));
                }

                Set<Integer> genreIdsToRemove = new HashSet<>(existingGenreIds);
                genreIdsToRemove.removeAll(updatedGenreIds);

                Set<Integer> genreIdsToAdd = new HashSet<>(updatedGenreIds);
                genreIdsToAdd.removeAll(existingGenreIds);

                if (!genreIdsToRemove.isEmpty()) {
                    String sqlDeleteGenres = "DELETE " + "FROM FilmGenre " + "WHERE film_id=? AND genre_id IN (%s)";

                    String genreIdsToRemoveStr = genreIdsToRemove.stream().map(String::valueOf).collect(Collectors.joining(", "));
                    String formattedSql = String.format(sqlDeleteGenres, genreIdsToRemoveStr);
                    jdbcTemplate.update(formattedSql, film.getId());
                }

                if (!genreIdsToAdd.isEmpty()) {
                    String sqlInsertGenre = "INSERT INTO FilmGenre VALUES (?, ?)";
                    genreIdsToAdd.forEach(genreId -> jdbcTemplate.update(sqlInsertGenre, film.getId(), genreId));
                }

                List<Genre> updatedGenres = new ArrayList<>();
                String sqlGenre = "SELECT DISTINCT genre_id FROM FilmGenre WHERE film_id=? ORDER BY genre_id ASC";
                SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlGenre, film.getId());
                while (rs.next()) {
                    updatedGenres.add(genreStorage.findGenreById(rs.getInt("genre_id")));
                }
                updatedGenres.sort(Comparator.comparingInt(Genre::getId));
                film.setGenres(updatedGenres);
            } else {
                film.setGenres(new ArrayList<>());
            }
        }

        updateDirectors(film);

        return film;
    }

    @Override
    public Film findFilmById(long id) {
        try {
            String sqlQuery = "SELECT * FROM FILM as f " + "WHERE film_id = ?";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильм с Id " + id + " не найден");
        }
    }

    public void deleteFilmById(long filmId) {
        String sql = "DELETE FROM Film WHERE film_id =?";
        jdbcTemplate.update(sql, filmId);
    }

    public List<Film> getRecommendations(Long userId) {
        String sqlQuery = "SELECT f.* FROM film f " + "JOIN (SELECT DISTINCT l2.film_id, COUNT(*) relevation " + "FROM Film_like l1 " + "LEFT JOIN Film_like l2 ON l1.user_id = l2.user_id " + "WHERE l1.film_id IN (SELECT film_id FROM Film_like WHERE user_id = ?) " + "AND l2.film_id NOT IN (SELECT film_id FROM Film_like WHERE user_id = ?) " + "GROUP BY l1.user_id, l2.film_id " + "ORDER BY relevation DESC) AS r " + "ON r.film_id = f.film_id";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, userId, userId);
    }


    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {

        return Film.builder().id(rs.getLong("film_id")).name(rs.getString("name")).description(rs.getString("description")).releaseDate(rs.getDate("release_date").toLocalDate()).duration(rs.getLong("duration")).rate(rs.getInt("rate")).genres(genreStorage.getFilmGenres(rs.getLong("film_id"))).mpa(mpaStorage.findMPAById(rs.getInt("age_id"))).directors(directorStorage.findDirectorsByFilm(rs.getLong("film_id"))).build();
    }

    public List<Film> getDirectorFilms(Long directorId, String sortBy) {
        String sql;
        if (sortBy.equals("year")) {
            sql = "SELECT * " + "FROM film WHERE film_id IN (SELECT film_id " + "FROM director_films " + "WHERE director_id =? )" + "ORDER BY release_date";
        } else {
            sql = "SELECT * " + "FROM film WHERE film_id IN (SELECT film_id " + "FROM director_films " + "WHERE director_id =? )" + "ORDER BY rate DESC";
        }

        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeFilm(rs)), directorId);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        return Film.builder().id(rs.getLong("film_id")).name(rs.getString("name")).description(rs.getString("description")).releaseDate(Objects.requireNonNull(rs.getDate("release_date")).toLocalDate()).duration(rs.getLong("duration")).rate(rs.getInt("rate")).genres(genreStorage.getGenresByFilm(rs.getLong("film_id"))).mpa(mpaStorage.findMPAById(rs.getInt("age_id"))).directors(directorStorage.findDirectorsByFilm(rs.getLong("film_id"))).build();
    }

    @Override
    public List<Film> getCommonFilms(Long userId, Long friendId) {
        String sql = "SELECT f.film_id " + "FROM Film AS f " + "JOIN Film_like AS l ON f.film_id = l.film_id " + "WHERE f.film_id IN (SELECT film_id " + "FROM Film_like AS l2 " + "WHERE user_id IN (?,?) " + "GROUP BY film_id " + "HAVING COUNT(user_id) = 2) " + "GROUP BY f.film_id " + "ORDER BY f.rate DESC";
        return jdbcTemplate.queryForList(sql, Integer.class, userId, friendId).stream().map(this::findFilmById).collect(Collectors.toList());
    }

    @Override
    public List<Film> getPopular(Integer limit, Integer genreId, Integer year) {
        String sq = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rate,age_id, " + "COUNT(l.user_id) AS COUNT " + "FROM FILM f " + "LEFT JOIN FilmGenre fg on f.film_id = fg.film_id " + "LEFT JOIN Film_like l on f.film_id = l.film_id {} GROUP BY f.film_id ORDER BY COUNT DESC LIMIT ?";
        if (genreId == null && year == null) {
            return jdbcTemplate.query(sq.replace("{}", ""), this::mapRowToFilm, limit);
        } else if (genreId == null) {
            return jdbcTemplate.query(sq.replace("{}", "WHERE EXTRACT(YEAR FROM release_date) = ?"), this::mapRowToFilm, year, limit);
        } else if (year == null) {
            return jdbcTemplate.query(sq.replace("{}", "WHERE genre_id = ?"), this::mapRowToFilm, genreId, limit);
        } else {
            return jdbcTemplate.query(sq.replace("{}", "WHERE genre_id = ? " + "AND EXTRACT(YEAR FROM release_date) = ? "), this::mapRowToFilm, genreId, year, limit);
        }
    }
}