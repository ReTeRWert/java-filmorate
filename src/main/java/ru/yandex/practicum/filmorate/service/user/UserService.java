package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Data
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUser(Long userId) throws NotFoundException {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return user;
    }

    public void delete(Long id) throws NotFoundException {
        if (userStorage.get(id) != null) {
            userStorage.delete(id);
        } else {
            throw new NotFoundException("User with id = " + id + " not found");
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
            userStorage.addFriend(userId, friendId);
            return user;
        }
    }

    public User deleteFriend(Long idUser, Long idFriend) {
        if (!(userStorage.deleteFriend(idUser, idFriend))) {
            throw new NotFoundException("Not friendship");
        }
        return userStorage.get(idUser);
    }

    public Collection<User> getFriendsUser(Long userId) throws NotFoundException {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else {
            return userStorage.getFriendsUser(userId);
        }
    }
}
