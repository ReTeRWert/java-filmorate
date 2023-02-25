package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class UserController {

    InMemoryUserStorage storage;
    UserService service;

    @Autowired
    public UserController(InMemoryUserStorage storage, UserService service) {
        this.storage = storage;
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return storage.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        return storage.getUserById(userId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return service.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getGeneralFriends(@PathVariable(value = "id") Integer verifiableUser,
                                        @PathVariable(value = "otherId") Integer comparedUser) {

        return new ArrayList<>(service.getListGeneralFriends(verifiableUser, comparedUser));
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws ValidateException {
        storage.addUser(user);
        log.info("Добавлен новый пользователь: {}", user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws ValidateException {
        storage.updateUser(user);
        log.info("Пользователь обновлен: {}", user);
        return user;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable(value = "id") Integer userId, @PathVariable Integer friendId) {
        service.addFriend(userId, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable(value = "id") Integer userId,
                             @PathVariable Integer friendId) {

        service.removeFriend(userId, friendId);
    }
}
