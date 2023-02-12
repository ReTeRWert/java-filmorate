package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.Login;

import java.time.LocalDate;

@Data
@Builder
@Validated
public class User {
    private final static Logger log = LoggerFactory.getLogger(User.class);
    @PositiveOrZero(message = "Идентификатор не может быть отрицательным")
    private Long id;
    @Email(message = "*@*.*")
    @NotBlank(message = "Can not be blank")
    private String email;
    @Login
    private String login;
    @Setter(AccessLevel.NONE)
    private String name;
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private LocalDate birthday;

    public String getName() {
        if (name == null || name.isBlank()) {
            return login;
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
