package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {
    void addFriend(Long userId, Long friendId);

    List<User> getFriendsForUser(Long userId);

    List<User> getCommonFriends(Long userId, Long otherUserId);

    void deleteFromFriends(Long userId, Long friendId);
}