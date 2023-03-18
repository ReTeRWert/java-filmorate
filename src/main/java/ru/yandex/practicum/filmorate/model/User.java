package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;

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

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
