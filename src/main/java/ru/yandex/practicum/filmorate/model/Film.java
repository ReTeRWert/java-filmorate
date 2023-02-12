package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.DateFilm;


import java.time.LocalDate;

@Data
@Builder
@Validated
public class Film {
    private final static Logger log = LoggerFactory.getLogger(Film.class);

    @PositiveOrZero(message = "Идентификатор не может быть отрицательным")
    Long id;
    @NotBlank(message = "Название не может быть пустым;")
    String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    String description;
    @DateFilm(message = "дата релиза — не раньше 28 декабря 1895 года")
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    Long duration;
}
