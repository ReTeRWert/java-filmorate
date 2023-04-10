package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    private Director director;


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

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa,
                Integer rate, List<Genre> genres, Director director) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.rate = rate;
        this.genres = genres;
        this.director = director;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
