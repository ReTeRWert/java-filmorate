package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.DateFilm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.model.Constants.MAX_LENGTH_DESCRIPTION_FILM;

@Data
@Builder
@Validated
public class Film {
    private final static Logger log = LoggerFactory.getLogger(Film.class);
    @PositiveOrZero(message = "Идентификатор не может быть отрицательным")
    private Long id;
    @NotBlank(message = "Название не может быть пустым;")
    private String name;
    @Size(max = MAX_LENGTH_DESCRIPTION_FILM, message = "Максимальная длина описания — "+MAX_LENGTH_DESCRIPTION_FILM+" символов")
    private String description;
    @DateFilm(message = "дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private Long duration;
}
