package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    @Override
    // тут то же самое, что и в Film
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(login, user.login) &&
                Objects.equals(name, user.name) &&
                Objects.equals(birthday, user.birthday);
    }
}
