package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
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


    @PostMapping
    public User create(@RequestBody @Validated User user) throws RuntimeException{
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@RequestBody @Validated User user) throws RuntimeException {
        return userService.getUserStorage().update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) throws RuntimeException{
        //log.debug("Попытка добавить друга{}",id);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws RuntimeException {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws RuntimeException {
        return userService.getUserStorage().get(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> allFriendsUser(@PathVariable Long id) throws RuntimeException{
        return userService.getFriendsUser(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId)
            throws RuntimeException{
        return userService.getUserStorage().getCommonFriends(id, otherId);
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
