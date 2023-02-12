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
    private long lastId = 0;
    private final HashMap<Long, User> userHashMap = new HashMap();

    @GetMapping("/users")
    public Collection<User> getUserHashMap() {
        log.info("Запрошен список пользователей");
        return userHashMap.values();
    }

    @PostMapping("/user")
    public User create(@RequestBody @Validated User user) {
        user.setId(++lastId);
        //user.setName(user.getName());
        log.info("Добавлен пользователь: {}", user);
        return userHashMap.put(user.getId(), user);
    }

    @PutMapping("/user")
    public User update( @RequestBody @Validated User user) {
        //user.setName(user.getName());
        log.info("Обновление пользователя");
        return userHashMap.put(user.getId(), user);
    }
}
