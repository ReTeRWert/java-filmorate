package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTest {

    private final MpaDbStorage mpaDbStorage;
    private final JdbcTemplate jdbcTemplate;

    private void addMpa() {
        Mpa mpa = new Mpa(1, "G");

        String sql = "INSERT INTO rating_mpa (rating_id, rating_name) " +
                "VALUES (?,?)";
        jdbcTemplate.update(sql, mpa.getId(),mpa.getName());
    }

    @Test
    void getRatingById() {
        Mpa mpa = mpaDbStorage.getRatingById(1);

        assertEquals("G", mpa.getName());
    }

    @Test
    void getRatings() {
        List<Mpa> mpas = mpaDbStorage.getRatings();

        assertNotNull(mpas);
        assertEquals(5, mpas.size());
    }
}
