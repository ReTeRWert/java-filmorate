package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<Long, User> userHashMap = new HashMap();
    private long lastId = 0;

    @GetMapping("/users")
    public Collection<User> getUserHashMap() {
        log.info("Запрошен список пользователей");
        return userHashMap.values();
    }

    @PostMapping("/users")
    public User create(@RequestBody @Validated User user) {
        user.setId(++lastId);
        log.info("Добавлен пользователь: {}", user);
        userHashMap.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody @Validated User user) {
        if (userHashMap.containsKey(user.getId())) {
            log.info("Обновление пользователя: {}", userHashMap.put(user.getId(), user));
            return user;
        } else {
            return null;
        }
    }
}
