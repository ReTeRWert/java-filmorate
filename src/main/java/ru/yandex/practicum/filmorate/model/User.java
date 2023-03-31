package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.validator.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder(toBuilder = true)
public class User {
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private Set<Long> friends;
    private Long id;
    @Email(message = "*@*.*")
    @NotBlank(message = "Can not be blank")
    private String email;
    @Login()
    private String login;
    @Getter(AccessLevel.NONE)
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

    private static AtomicLong counter = new AtomicLong(0);

    public static void setCounter(AtomicLong counter) {
        User.counter = counter;
    }

    public static Long setIdCounter() {
        return counter.incrementAndGet();
    }
}
