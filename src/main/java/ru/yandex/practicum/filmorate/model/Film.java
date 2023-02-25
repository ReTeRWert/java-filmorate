package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private HashSet<String> likes = new HashSet<>();
}
