package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.validator.DateFilm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static ru.yandex.practicum.filmorate.model.Constants.MAX_LENGTH_DESCRIPTION_FILM;

@Data
@Builder(toBuilder = true)
public class Film {
    private final static Logger log = LoggerFactory.getLogger(Film.class);
    //@Setter(AccessLevel.NONE)
    private Set<Long> likes;
    private Long id;
    @NotBlank(message = "Название не может быть пустым;")
    private String name;
    @Size(max = MAX_LENGTH_DESCRIPTION_FILM, message = "Максимальная длина описания — " + MAX_LENGTH_DESCRIPTION_FILM + " символов")
    private String description;
    private MPA mpa;
    private LinkedHashSet<Genre> genres;
    @DateFilm(message = "дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private Long duration;

    private static AtomicLong counter = new AtomicLong(0);

    public static void setCounter(AtomicLong counter) {
        Film.counter = counter;
    }

    public static Long setIdCounter() {
        return counter.incrementAndGet();
    }
}
