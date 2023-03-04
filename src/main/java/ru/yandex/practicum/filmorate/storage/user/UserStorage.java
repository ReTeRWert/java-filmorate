package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);

    User update(User user);

    boolean delete(long userId);

    User get(long id);

    Collection<User> getUsers();

    void addFriend(Long userId, Long friendId);

    Collection<User> getFriendsUser(Long userId);

    Collection<User> getCommonFriends(Long userId, Long friendId);

    boolean deleteFriend(long userId, long friendId);
}
