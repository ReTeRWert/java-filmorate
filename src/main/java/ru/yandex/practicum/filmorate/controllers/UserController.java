package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utilites.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private static int id = 1;


    @GetMapping("/users")
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws ValidateException {

        validator.validateUser(user);

        if (users.containsValue(user)) {
            throw new ValidateException("Такой пользователь уже есть.");
        }

        user.setId(id);
        users.put(user.getId(), user);
        id++;
        log.info("Добавлен новый пользователь: {}", user);

        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws ValidateException {

        validator.validateUser(user);

        if (!users.containsKey(user.getId())) {
            throw new ValidateException("Такого пользователя еще нет, добавьте его");
        }

        users.replace(user.getId(), user);
        log.info("Пользователь обновлен: {}", user);

        return user;
    }
}
