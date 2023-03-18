package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;

import java.time.LocalDate;
import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Film {
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Mpa mpa;
    private Integer rate = 0;
    private HashSet<Integer> likes = new HashSet<>();
    private List<Genre> genres = new ArrayList<>();


    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration,
                Mpa ratingMpa, Integer rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = ratingMpa;
        this.rate = rate;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
