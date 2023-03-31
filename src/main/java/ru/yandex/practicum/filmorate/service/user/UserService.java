package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Data
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;
    private final FilmStorage filmStorage;


    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage,
                       @Qualifier("friendshipDbStorage") FriendshipStorage friendshipStorage,
                       @Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
        this.filmStorage = filmStorage;
    }

    public User create(User user) {
        userStorage.create(user);
        return user;
    }

    public User update(User user) {
        if (userStorage.get(user.getId()) != null) {
            return userStorage.update(user);
        } else {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
    }

    public void delete(Long id) throws NotFoundException {
        if (userStorage.get(id) != null) {
            userStorage.remove(id);
        } else {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
    }

    public User addFriend(Long userId, Long friendId) throws NotFoundException {
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(friendId);

        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else if (friend == null) {
            throw new NotFoundException("User with id = " + friendId + " not found");
        } else {
            friendshipStorage.addFriend(userId, friendId);
            return userStorage.update(user);
        }
    }

    public User deleteFriend(Long userId, Long friendId) throws NotFoundException {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);

        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else if (friend == null) {
            throw new NotFoundException("User with id = " + friendId + " not found");
        } else {
            friendshipStorage.deleteFromFriends(userId, friendId);
            return userStorage.update(user);
        }
    }

    public User get(Long userId) throws NotFoundException {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return user;
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getFriendsUser(Long userId) throws NotFoundException {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else {
            List<User> friendsList = friendshipStorage.getFriendsForUser(userId);
            return friendsList;
        }
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) throws NotFoundException {
        User user = userStorage.get(userId);
        User otherUser = userStorage.get(otherUserId);

        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        } else if (otherUser == null) {
            throw new NotFoundException("User with id = " + otherUserId + " not found");
        } else {
            return friendshipStorage.getCommonFriends(userId, otherUserId);
        }
    }

}
