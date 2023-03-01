package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUserStorage().getUsers();
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) throws RuntimeException {
        return userService.getUserStorage().get(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> allFriendsUser(@PathVariable long id) throws RuntimeException{
        return userService.getFriendsUser(id);
    }

    @PostMapping
    public User create(@RequestBody @Validated User user) throws RuntimeException{
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@RequestBody @Validated User user) throws RuntimeException {
        return userService.getUserStorage().update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) throws RuntimeException{
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws RuntimeException {
        userService.deleteFriend(id, friendId);
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
