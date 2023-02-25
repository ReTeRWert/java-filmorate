package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @EqualsAndHashCode.Exclude
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private HashSet<Integer> friends = new HashSet<>();
}
