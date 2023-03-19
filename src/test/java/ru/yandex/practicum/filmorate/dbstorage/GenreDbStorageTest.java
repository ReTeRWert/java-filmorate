package ru.yandex.practicum.filmorate.dbstorage;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {

    private final GenreDbStorage genreDbStorage;

    @Test
    void getGenreById() {
        Genre drama = genreDbStorage.getGenreById(2);

        assertEquals("Драма", drama.getName());
    }

    @Test
    void getGenres() {
        List<Genre> genres = genreDbStorage.getGenres();

        assertNotNull(genres);
        assertEquals(6, genres.size());
    }
}
