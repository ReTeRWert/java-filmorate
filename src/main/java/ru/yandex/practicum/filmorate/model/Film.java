package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;


    @Override
    // Тут equals не сравнивает строку id, т.к. при добавлении фильма он приходит без него, и получается что
    // можно добавить один и тот же фильм несколько раз, потому что у того фильма, который уже в мапе, есть id, а у
    // нового еще нет, и они будут считаться разными объектами.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(duration, film.duration) &&
                Objects.equals(name, film.name) &&
                Objects.equals(description, film.description) &&
                Objects.equals(releaseDate, film.releaseDate);
    }
}
