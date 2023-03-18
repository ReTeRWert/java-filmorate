package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    void addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);

    List<User> getFriendsUser(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer friendId);
}
