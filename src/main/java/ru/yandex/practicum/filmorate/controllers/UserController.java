package ru.yandex.practicum.filmorate.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.utilites.UserValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserValidator validator;
    private final UserService service;


    @PostMapping
    public User addUser(@RequestBody User user) throws ValidateException {
        validator.validateUser(user);
        service.addUser(user);
        log.info("Добавлен новый пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidateException {
        validator.validateUser(user);
        service.updateUser(user);
        log.info("Пользователь обновлен: {}", user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer userId) {
        service.removeUserById(userId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        return service.getUserById(userId);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }


    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return service.getFriendsUser(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(value = "id") Integer verifiableUser,
                                       @PathVariable(value = "otherId") Integer comparedUser) {

        return new ArrayList<>(service.getListCommonFriends(verifiableUser, comparedUser));
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable(value = "id") Integer userId, @PathVariable Integer friendId) {
        service.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable(value = "id") Integer userId,
                             @PathVariable Integer friendId) {

        service.removeFriend(userId, friendId);
    }
}
