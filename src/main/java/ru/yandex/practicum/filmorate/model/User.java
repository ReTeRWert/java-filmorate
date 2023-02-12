package ru.yandex.practicum.filmorate.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
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
