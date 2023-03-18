package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDbStorage userDbStorage;
    private final FriendDbStorage friendDbStorage;


    public void addUser(User user) {
        if (userDbStorage.getUsers().contains(user)) {
            throw new ValidateException("Такой пользователь уже есть.");
        }

        userDbStorage.addUser(user);
    }

    public void updateUser(User user) {
        checkUser(user.getId());

        userDbStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userDbStorage.getUsers();
    }

    public User getUserById(Integer userId) {
        checkUser(userId);

        return userDbStorage.getUserById(userId);
    }

    public void removeUserById(Integer userId) {
        checkUser(userId);

        userDbStorage.removeUserById(userId);
    }


    public void addFriend(Integer userId, Integer friendId) {
        checkUser(userId);
        checkUser(friendId);

        friendDbStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        checkUser(userId);
        checkUser(friendId);

        friendDbStorage.removeFriend(userId, friendId);
    }

    public List<User> getListCommonFriends(Integer userId, Integer friendId) {
        checkUser(userId);
        checkUser(friendId);

        return friendDbStorage.getCommonFriends(userId, friendId);
    }

    public List<User> getFriendsUser(Integer userId) {
        checkUser(userId);

        return friendDbStorage.getFriendsUser(userId);
    }


    private void checkUser(Integer userId) {
        if (userDbStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователя с id: " + userId + " не существует.");
        }
    }
}
