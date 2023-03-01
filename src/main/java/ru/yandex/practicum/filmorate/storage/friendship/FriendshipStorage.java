package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipStorage {
    void addFriend(long userId, long friendId);

    Collection<User> getFriendsUser(long userId);

    Collection<User> getCommonFriends(long userId, long friendId);

    boolean deleteFriend(long userId, long friendId);
}
