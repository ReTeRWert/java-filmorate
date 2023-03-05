package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Data
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    private long lastId = 0L;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        user.setId(++lastId);
        userStorage.add(user);
        return user;
    }

    public User getUser(Long userId) throws NotFoundException {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return user;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void delete(Long id) throws NotFoundException {
        if (userStorage.get(id) != null) {
            userStorage.delete(id);
        } else {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
    }

    public User update(User user) {
        if (userStorage.get(user.getId()) != null) {
            return userStorage.add(user);
        } else {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
    }

    public User addFriend(Long userId, Long friendId) throws NotFoundException {
        if (userId == friendId) {
            log.debug("Пользователь не может быть другом самому себе");
            throw new ValidationException("Ошибка валидации");
        }
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(friendId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else if (friend == null) {
            throw new NotFoundException("User with id = " + friendId + " not found");
        } else {
            user.getFriends().put(friendId, true);
            friend.getFriends().put(userId, true);
            userStorage.add(user);
            userStorage.add(friend);
            return user;
        }
    }

    public User deleteFriend(Long idUser, Long idFriend) {
        User user = userStorage.get(idUser);
        if (user.getFriends().remove(idFriend)) {
            return userStorage.add(user);
        } else {
            throw new NotFoundException("Not friendship");
        }
    }

    public Collection<User> getFriendsUser(Long userId) throws NotFoundException {
        User user = userStorage.get(userId);
        Collection<User> friendsUser = new ArrayList<>();
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else {
            for (Long n : user.getFriends().keySet()) friendsUser.add(userStorage.get(n));
        }
        return friendsUser;
    }

    public Collection<User> getCommonFriends(Long userId, Long friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        Collection<User> friendsUser = new ArrayList<>();
        if (user != null && friend != null && user != friend) {
            HashMap<Long, Boolean> friendListFirst = new HashMap<>(user.getFriends());
            HashMap<Long, Boolean> friendListSecond = new HashMap<>(friend.getFriends());
            friendListFirst.keySet().retainAll(friendListSecond.keySet());
            for (Long n : friendListFirst.keySet()) friendsUser.add(userStorage.get(n));
        }
        return friendsUser;
    }
}
