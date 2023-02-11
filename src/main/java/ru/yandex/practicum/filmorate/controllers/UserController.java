package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utilites.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    Validator validator = new Validator();


    @GetMapping("/users")
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user, HttpServletRequest request) {
        try {
            validator.validateUser(user);
        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }

        if (users.containsValue(user)) {
            System.out.println("Такой пользователь уже есть.");
            return null;
        }

        users.put(user.getId(), user);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            validator.validateUser(user);
        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }

        if (!users.containsValue(user)) {
            System.out.println("Такого пользователя еще нет, добавьте его");
            return null;
        }

        users.replace(user.getId(), user);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return user;
    }
}
